import React from 'react';

/**
 * id: String
 * className: String
 * children: String
 * onClick: function
 */
export default class Button extends React.Component {
    render() {
        return <button type='button' className={this.props.className} id={this.props.id} onClick={this.props.onClick}>
            {this.props.children}
        </button>
    }
}