import React from 'react';

/**
 * id: String
 * label: String
 * name: String
 * maxLength: number
 * value: String
 * onChange: function
 */
export default class TextArea extends React.Component {

    onChange = (event) => {
        const newValue = event.currentTarget.value;
        if (!this.props.maxLength || newValue.length <= this.props.maxLength) {
            this.props.onChange(newValue);
        }
    };

    render() {
        return <div>
            <label htmlFor={`for-${this.props.id}`}>{this.props.label}</label>
            <textarea
                name={this.props.name}
                id={`for-${this.props.id}`}
                className={this.props.className || ''}
                value={this.props.value || ''}
                onChange={this.onChange}
            />
        </div>
    }
}