import React from 'react';

/**
 * id: String
 * name: String
 * elements: [{
 *      name: String,
 *      id: String,
 *      active: boolean
 * }]
 * label: boolean
 * readOnly: boolean
 * className: String
 * onChange: function
 */
export default class Checkbox extends React.Component {

    EMPTY_ELEMENT = -1;

    onChange = (value) => {
        value = value === this.EMPTY_ELEMENT ? null : value;
        this.props.onChange(value);
    };

    render() {
        return <div>
            <label htmlFor={`for-${this.props.id}`}>{this.props.label}</label>
            <select className={`${this.props.className || ''} ${this.props.error ? 'input-error' : ''}`}
                    name={this.props.name}
                    disabled={this.props.readOnly}
                    id={`for-${this.props.id}`}
                    onChange={e => this.onChange(e.currentTarget.value)}
            >
                {this.props.elements.map(e =>
                    <option value={e.id} key={e.id || this.EMPTY_ELEMENT} selected={e.active}>
                        {e.name}
                    </option>)}
            </select>
        </div>
    }
}