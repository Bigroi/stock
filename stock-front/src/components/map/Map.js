import React from 'react';
import {GoogleApiWrapper, Map, Marker} from 'google-maps-react';
import Geocode from 'react-geocode';
import Config from '../../util/Config';

export const geocode = (address) => {
    Geocode.setApiKey(Config.getConfig().googleMapsApiKey);
    const addressStr = `${address.country} ${address.city} ${address.address}`;
    return Geocode.fromAddress(addressStr).then(
        response => {
            const { lat, lng } = response.results[0].geometry.location;
            return {
                latitude: lat,
                longitude: lng
            }
        }
    );
};

export class MapContainer extends React.Component {

    getPosition = () => {
        const defaultLocation = {latitude: 53.888069, longitude: 27.537102};
        const coords = this.props.coords || defaultLocation;
        return {
            lat: coords.latitude || defaultLocation.latitude,
            lng: coords.longitude || defaultLocation.longitude
        };
    };

    render() {
        return (
            <Map google={this.props.google} zoom={10} initialCenter={this.getPosition()} center={this.getPosition()}>
                <Marker position={this.getPosition()}/>
            </Map>
        );
    }
}

export default GoogleApiWrapper({
    apiKey: Config.getConfig().googleMapsApiKey
})(MapContainer)