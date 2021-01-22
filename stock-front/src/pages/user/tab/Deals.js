import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from '../../../util/Request';
import ApiUrls from '../../../util/ApiUrls';
import Table from '../../../components/table/Table';

class Deals extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        return Request.doGet(ApiUrls.DEAL_LIST)
            .then(async response => {
                if (response.ok) {
                    this.setState({data: JSON.parse(await response.text()).sort(this.sortDeals)});
                }
                Request.doPut(ApiUrls.ALERTS_DEALS)
                    .then(response => {
                        if (response.ok) {
                            this.props.resetAlerts();
                        }
                    });
            });
    }

    sortDeals = (deal1, deal2) => {
        const statusToInt = (status) => {
            switch (status) {
                case 'ON_APPROVE':
                    return 0;
                case 'ON_PARTNER_APPROVE':
                    return 1;
                case 'APPROVED':
                    return 2;
                case 'REJECTED':
                    return 2;
                default:
                    throw new Error();
            }
        };
        return statusToInt(deal1.status) - statusToInt(deal2.status)
            || new Date(deal1.creationDate) - new Date(deal2.creationDate);
    };

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.deal.productName'), weight: 1},
            {name: t('label.deal.categoryName'), weight: 4},
            {name: t('label.deal.time'), weight: 5},
            {name: t('label.deal.status'), weight: 3},
            {name: t('label.deal.edit'), weight: 1},
        ]
    };

    getData = () => {
        const {t} = this.props;
        return this.state.data.map(d => {
            return [
                t(`label.${d.productName}.name`),
                t(`label.${d.categoryName}.name`),
                d.creationDate,
                t(`label.deal.${d.status.toLowerCase()}`),
                <div className='details' onClick={() => this.props.onDetails(d.id)}>
                    {t('label.table.details')}
                </div>
            ]
        })
    };

    render() {
        return <Table
            data={this.getData()}
            header={this.getHeader()}
            pageSize={10}
        />;
    }
}

export default withTranslation()(Deals);