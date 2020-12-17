import React from 'react';

/**
 * message: {
 *     type: Message.TYPES,
 *     value: String
 * }
 */
export const TYPES = {
    ERROR: 'error-message',
    SUCCESS: 'success-message'
};
export default class Message extends React.Component {

    render() {
        const message = this.props.message || {type: '', value: ''};
        return <div className={`dialogbox-message ${message.type}`}>{message.value}</div>
    }
}