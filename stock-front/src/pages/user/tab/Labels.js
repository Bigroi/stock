import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";
import Form from "../../../components/form/Form";
import LotForm from "../../../forms/lot/LotForm"
import LabelForm from "../../../forms/label/LabelForm";

class Categories extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            editLabel: null
        }
    }

    componentDidMount() {
        return Request.doGet(ApiUrls.LABELS_ADMIN)
            .then(async response => {
                if (response.ok) {
                    this.setState({data: JSON.parse(await response.text())});
                }
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.labels.name'), weight: 1},
            {name: t('label.labels.english'), weight: 3},
            {name: t('label.labels.poland'), weight: 4},
            {name: t('label.labels.russian'), weight: 5},
            {name: t('label.labels.edit'), weight: 1},
        ]
    };

    remove = (name) => {
        Request.doDelete(ApiUrls.LABEL_ADMIN.replace("{name}", name))
            .then(response => {
                if (response.ok) {
                    this.setState({data: this.state.data.filter(l => l.name !== name)});
                }
            });
    };

    getData = () => {
        return this.state.data.map(l => {
            return [
                l.name,
                l.labels.EN,
                l.labels.PL,
                l.labels.RU,
                <React.Fragment>
                    <div className='edit' onClick={() => this.setState({editLabel: l})}/>
                    <div className='remove' onClick={() => this.remove(l.name)}/>
                </React.Fragment>
            ]
        })
    };

    closeForm = () => {
        this.setState({editLabel: null});
        this.props.onCloseForm();
    };

    getForm = () => {
        if (this.state.editLabel || this.props.showEmptyForm) {
            return <Form
                className='label-dialogbox'
                onClose={this.closeForm}
            >
                <LabelForm onSave={this.changeLabelList} label={this.state.editLabel ? this.state.editLabel : null}/>
            </Form>
        }
    };

    changeLabelList = (label, isNew) => {
        const labels = this.state.data.slice();
        if (isNew) {
            labels.splice(0, 0, label);
        } else {
            const labelFromList = labels.find(l => l.id === label.id);
            Object.keys(labelFromList)
                .forEach(propertyName => labelFromList[propertyName] = label[propertyName]);
        }
        this.setState({data: labels});
        this.closeForm();
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

export default withTranslation()(Categories);