import React from 'react';
import {withTranslation} from 'react-i18next';

class LotForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            lot: this.props.lot
                || {
                    id: null,
                    description: null,
                    price: null,
                    minVolume: null,
                    maxVolume: null,
                    companyId: null,
                    status: null,
                    creationDate: null,
                    expirationDate: null,
                    addressId: null,
                    distance: null,
                    categoryId: null,
                    alert: null,
                    photo: null
                },
        }
    }

    render() {
        return
    }

}

export default withTranslation()(LotForm);