import React from 'react';

/**
 * id: String
 * label: String
 * type: String default text
 * name: String
 * placeholder: String
 * maxLength: number
 * value: String
 * error: boolean
 * onChange: function
 * disabled: boolean
 * underline: html element
 */
export default class Input extends React.Component {

    onChange = (event) => {
        const newValue = event.currentTarget.value;
        if (!this.props.maxLength || newValue.length <= this.props.maxLength) {
            this.props.onChange(newValue);
        }
    };

    render() {
        return <div>
            <label htmlFor={`for-${this.props.id}`}>{this.props.label}</label>
            <input
                className={`${this.props.className || ''} ${this.props.error ? 'input-error' : ''}`}
                value={this.props.value || ''}
                type={this.props.type || 'text'}
                name={this.props.name}
                id={`for-${this.props.id}`}
                placeholder={this.props.placeholder}
                disabled={!!this.props.disabled}
                onChange={this.onChange}
            />
            {this.props.underline || ''}
        </div>
    }
}