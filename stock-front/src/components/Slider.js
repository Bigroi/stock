import React, {Component} from 'react';
import SlickSlider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

export default class Slider extends Component {

    render() {
        const settings = {
            dots: false,
            infinite: false,
            autoplay: true,
            speed: 4000,
            slidesToShow: 2,
            slidesToScroll: 1,
            initialSlide: 1,
            responsive: [
                {
                    breakpoint: 1300,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }
                }
            ]
        };
        return (
            <SlickSlider {...settings}>
                {this.props.elements.map((e, index) => <div key={`slide-${index}`}>{e}</div>)}
            </SlickSlider>
        );
    }
}