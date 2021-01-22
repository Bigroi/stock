import React from 'react';

/**
 * id: String
 * name: String
 * label: String
 * onChange: function
 */
export default class File extends React.Component {

    linkFile = (event) => {
        const reader = new FileReader();
        reader.onload = function () {
            this.props.onChange(reader.result);
        }.bind(this);
        reader.readAsDataURL(event.target.files[0]);
    };


    render() {
        return <div>
            <label htmlFor={`for-${this.props.id}`}>{this.props.label}</label>
            <input type="file" name={this.props.name} id={`for-${this.props.id}`} onChange={this.linkFile}/>
        </div>
    }

}