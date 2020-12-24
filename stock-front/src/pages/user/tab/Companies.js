import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";

class Companies extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        return Request.doGet(ApiUrls.COMPANIES_ADMIN)
            .then(async response => {
                if (response.ok) {
                    this.setState({ data: JSON.parse(await response.text())});
                }
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.companies.name'), weight: 1},
            {name: t('label.companies.phone'), weight: 3},
            {name: t('label.companies.reg_number'), weight: 4},
            {name: t('label.companies.status'), weight: 1},
        ]
    };

    changeStatus = (company) => {
        const baseUrl = company.status === 'REVOKED'
            ? ApiUrls.COMPANY_ADMIN_ACTIVATE
            : ApiUrls.COMPANY_ADMIN_REVOKE;
        Request.doPut(baseUrl.replaceAll('{id}', company.id))
            .then(response => {
                if (response.ok) {
                    const data = this.state.data.slice();
                    data.find(d => d.id === company.id).status = company.status === 'REVOKED' ? 'VERIFIED' : 'REVOKED';
                    this.setState({data: data});
                }
            });
    };

    getData = () => {
        return this.state.data.map(p => {
            return [
                p.name,
                p.phone,
                p.regNumber,
                <div className={`swtitch-row-${p.status === 'REVOKED' ? 'off' : 'on'}`}
                     onClick={() => this.changeStatus(p)}
                />
            ]
        })
    };

    render() {
        return <Table
                data={this.getData()}
                header={this.getHeader()}
                pageSize={10}
            />
    }
}

export default withTranslation()(Companies);