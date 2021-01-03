import React from 'react';
import Request from '../../../util/Request';
import ApiUrls from '../../../util/ApiUrls';

export default class Trade extends React.Component {

    act = (url, message) => {
        Request.doPut(url)
            .then(response => {
                if (response.ok) {
                    alert(message);
                }
            })
    };

    render() {
        return <div className=''>
            <div
                className='add-button'
                onClick={() => this.act(ApiUrls.TRADE_ADMIN_EXPIRATION_CHECK, 'expiration check is finished')}
            >
                <p>Check expired</p>
            </div>
            <br/>
            <div
                className='add-button'
                onClick={() => this.act(ApiUrls.TRADE_ADMIN_CLEAR_PREDEAL, 'predeals are cleaned')}
            >
                <p>Clean predeals</p>
            </div>
            <br/>
            <div
                className='add-button'
                onClick={() => this.act(ApiUrls.TRADE_ADMIN_RUN, 'trade session is finished')}
            >
                <p>Run trade session</p>
            </div>
        </div>;
    }
}