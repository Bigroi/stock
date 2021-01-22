import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from '../../../util/Request';
import ApiUrls from '../../../util/ApiUrls';
import Table from '../../../components/table/Table';
import Form from '../../../components/form/Form';
import AddressForm from '../../../forms/address/AddressForm';

class Addresses extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            editAddress: null
        }
    }

    componentDidMount() {
        return Request.doGet(ApiUrls.ADDRESS_LIST)
            .then(async response => {
                if (response.ok) {
                    this.setState({data: JSON.parse(await response.text())});
                }
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.address.city'), weight: 4},
            {name: t('label.address.country'), weight: 5},
            {name: t('label.address.address'), weight: 1},
            {name: t('label.address.default_address'), weight: 3},
            {name: t('label.address.edit'), weight: 1},
        ]
    };

    changeStatus = (address) => {
        if (address.primaryAddress) {
            return;
        }

        Request.doPut(ApiUrls.ADDRESS_PRIMARY.replace('{id}', address.id))
            .then(response => {
                if (response.ok) {
                    const data = this.state.data.slice();
                    data.find(a => a.primaryAddress).primaryAddress = false;
                    data.find(d => d.id === address.id).primaryAddress = true;
                    this.setState({data: data});
                }
            });
    };

    remove = (addressId) => {
        Request.doDelete(ApiUrls.ADDRESS.replace('{id}', addressId))
            .then(response => {
                if (response.ok) {
                    this.setState({data: this.state.data.filter(d => d.id !== addressId)});
                }
            });
    };

    closeForm = () => {
        this.setState({editAddress: null});
        this.props.onCloseForm();
    };

    updateList = (address, isNew) => {
        const addresses = this.state.data.slice();
        if (isNew) {
            addresses.splice(0, 0, address);
        } else {
            const addressFromList = addresses.find(l => l.id === address.id);
            addressFromList.address = address.address;
            addressFromList.city = address.city;
            addressFromList.county = address.country;
        }
        this.closeForm();
    };

    getForm = () => {
        if (this.state.editAddress || this.props.showEmptyForm) {
            return <Form
                map={true}
                coords={this.state.editAddress
                    ? {latitude: this.state.editAddress.latitude, longitude: this.state.editAddress.longitude}
                    : null
                }
                onClose={this.closeForm}
                className='address-dialogbox'
            >
                <AddressForm
                    address={this.state.editAddress ? this.state.editAddress : null}
                    onSave={this.updateList}
                />
            </Form>
        }
    };

    getData = () => {
        return this.state.data.map(c => {
            return [
                c.city,
                c.country,
                c.address,
                <div className={`swtitch-row-${c.primaryAddress ? 'on' : 'off'}`}
                     onClick={() => this.changeStatus(c)}
                />,
                <React.Fragment>
                    <div className='edit' onClick={() => this.setState({editAddress: c})}/>
                    {c.primaryAddress ? '' : <div className='remove' onClick={() => this.remove(c.id)}/>}
                </React.Fragment>
            ]
        })
    };

    render() {
        return <React.Fragment>
            {this.getForm()}
            <Table
                data={this.getData()}
                header={this.getHeader()}
                pageSize={10}
            />
        </React.Fragment>;
    }
}

export default withTranslation()(Addresses);