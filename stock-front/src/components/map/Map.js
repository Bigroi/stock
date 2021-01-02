import React from 'react';
import {withGoogleMap, withScriptjs, GoogleMap, Marker, DirectionsRenderer} from "react-google-maps";
import Geocode from 'react-geocode';
import Config from '../../util/Config';

export const geocode = (address) => {
    Geocode.setApiKey(Config.getConfig().googleMapsApiKey);
    const addressStr = `${address.country} ${address.city} ${address.address}`;
    return Geocode.fromAddress(addressStr).then(
        response => {
            const {lat, lng} = response.results[0].geometry.location;
            return {
                latitude: lat,
                longitude: lng
            }
        }
    );
};

export class MapContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            directions: null
        }
    }

    componentDidMount() {
        if (this.props.driveFrom) {
            // eslint-disable-next-line no-undef
            const DirectionsService = new google.maps.DirectionsService();
            // eslint-disable-next-line no-undef
            console.log(new google.maps.LatLng(41.8525800, -87.6514100));
            DirectionsService.route({
                // eslint-disable-next-line no-undef
                origin: new google.maps.LatLng(this.props.driveFrom.latitude, this.props.driveFrom.longitude),
                // eslint-disable-next-line no-undef
                destination: new google.maps.LatLng(this.props.coords.latitude, this.props.coords.longitude),
                travelMode: 'DRIVING'
            }, (result, status) => {
                if (status === 'OK') {
                    this.setState({directions: result});
                } else {
                    console.error('error fetching directions', result);
                }
            })
        }
    }

    getPosition = () => {
        const defaultLocation = {latitude: 53.888069, longitude: 27.537102};
        const coords = this.props.coords || defaultLocation;
        return {
            lat: coords.latitude || defaultLocation.latitude,
            lng: coords.longitude || defaultLocation.longitude
        };
    };

    render() {
        if (this.state.directions) {
            return (
                <GoogleMap>
                    {<DirectionsRenderer directions={this.state.directions}/>}
                </GoogleMap>
            );
        } else {
            return (
                <GoogleMap zoom={10} defaultCenter={this.getPosition()}>
                    <Marker position={this.getPosition()}/>
                </GoogleMap>
            );
        }
    }
}

const defaultProps = {
    googleMapURL: 'https://maps.googleapis.com/maps/api/js?v=3.exp&key=' + Config.getConfig().googleMapsApiKey,
    loadingElement: <div/>,
    containerElement: <div style={{height: '100%'}}/>,
    mapElement: <div style={{height: '100%'}}/>
};

const MapInner = withScriptjs(withGoogleMap(MapContainer));
const Map = props => <MapInner {...props} {...defaultProps}/>;

export default Map;