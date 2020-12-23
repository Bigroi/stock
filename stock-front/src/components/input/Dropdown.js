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

    render() {
        return <div>
            <label htmlFor={`for-${this.props.id}`}>{this.props.label}</label>
            <select className={`${this.props.className || ''} ${this.props.error ? 'input-error' : ''}`}
                    name={this.props.name}
                    disabled={this.props.readOnly}
                    id={`for-${this.props.id}`}
                    onChange={e => this.props.onChange(e.currentTarget.value)}
            >
                {this.props.elements.map(e =>
                    <option value={e.id} key={e.id} selected={e.active}>
                        {e.name}
                    </option>)}
            </select>
        </div>
    }
}