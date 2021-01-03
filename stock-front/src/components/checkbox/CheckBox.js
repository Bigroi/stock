import React from 'react';

/**
 * value: String
 * error: boolean
 * onChange: function
 * children: html element
 */
export default class Checkbox extends React.Component {

    render() {
        return <div className='for-checkbox'>
            <div className={`checkbox-title ${this.props.error ? 'checkbox-error' : ''}`}>
                <input type='checkbox' id='agree' name='' checked={this.props.value}
                       onChange={() => this.props.onChange(!this.props.value)}/>
                <label htmlFor='agree'/>
                {this.props.children}
            </div>
        </div>
    }
}