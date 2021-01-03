import React from 'react';
import {withTranslation} from 'react-i18next';
import Map, {geocode} from "../map/Map";

/**
 * map: boolean
 * className: String
 * onClose: function
 * children: Html element
 */
class Form extends React.Component {

    constructor(props) {
        super(props);
        this.state = {coords: this.props.coords};
    }

    changeAddress = (address, readyCords) => {
        if (readyCords) {
            this.setState({coords: address});
        } else {
            return geocode(address).then(coords => {
                this.setState({coords: coords});
                return coords;
            });
        }
    };

    render() {
        const childrenWithProps = React.Children.map(this.props.children, child => {
            if (React.isValidElement(child)) {
                return React.cloneElement(child, { onAddressChanged: this.changeAddress });
            }
            return child;
        });

        return <div id='form-container'>

            <div className={`dialogbox ${this.props.className}`}>
                <div className='dialogbox-child'>
                    <div className='left-part google-map-container'>
                        {this.props.map ? <Map coords={this.state.coords}/> : ''}
                    </div>
                    <div className='right-part'>
                        <div className='popUp-container'>
                            <div className='dialogbox-Head' onClick={this.props.onClose}>
                                <span className='dialogbox-spanClose'/>
                            </div>
                            <div className='dialogbox-Content'>
                                <div className='dialogbox-inner'>
                                    <div className='dialogbox-elementContent'>
                                        {childrenWithProps}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    }
}

export default withTranslation()(Form);