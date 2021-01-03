import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Message from "../../../components/message/Message";
import Input from "../../../components/input/Input";
import Button from "../../../components/input/Button";
import MapContainer from "../../../components/map/Map";
import Form from "../../../components/form/Form";
import CompanyFeedBackForm from "../../../forms/company-feed-back/CompanyFeedBackForm";

class DealDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            deal: {
                id: null,
                productName: '',
                categoryName: '',
                creationDate: new Date(),
                status: '',
                price: 0,
                volume: 0,
                partnerName: '',
                partnerMark: 0,
                partnerComment: '',
                packaging: '',
                processing: '',
                phone: '',
                regNumber: '',
                photo: '',
                address: '',
                latitude: 0,
                longitude: 0,
                fromLatitude: 0,
                fromLongitude: 0
            },
            feedBackForm: false,
            message: null
        }
    }

    componentDidMount() {
        Request.doGet(ApiUrls.DEAL.replace("{id}", this.props.dealId))
            .then(async response => {
                if (response.ok) {
                    this.setState({deal: JSON.parse(await response.text())});
                }
            });
    }

    updateChoice = (choice) => {
        Request.doPut(
            ApiUrls.DEAL_CHOICE
                .replace("{id}", this.props.dealId)
                .replace("{choice}", choice)
        ).then(async response => {
            if (response.ok) {
                this.setState({
                    deal: {
                        ...this.state.deal,
                        status: JSON.parse(await response.text())
                    }
                });
            }
        });
    };

    getForm = () => {
        if (this.state.feedBackForm) {
            return <Form
                className='dealFeedback-dialogbox'
                onClose={() => this.setState({feedBackForm: false})}
            >
                <CompanyFeedBackForm
                    dealId={this.props.dealId}
                    onClose={() => this.setState({feedBackForm: false})}
                />
            </Form>
        }
    };

    getStarClass = (mark) => {
        return this.state.deal.partnerMark >= mark
            ? 'reviewStarsDeal-start-checked'
            : 'reviewStarsDeal-start-unchecked'
    };

    getMark = () => {
        return <div className="reviewStarsDeal">
            {[5, 4, 3, 2, 1]
                .map(mark =>
                    <label
                        key={`deal-star-${mark}`}
                        className={this.getStarClass(mark)}
                    />
                )}
        </div>
    };

    getButtons = () => {
        const {t} = this.props;
        return <div className="footer-deal-form">
            <div>
                <Button className="submit gray-button" id="back-deals" onClick={this.props.onBack}>
                    {t('label.button.back')}
                </Button>
            </div>
            <div>
                {this.state.deal.status === 'ON_APPROVE'
                    ? <React.Fragment>
                        <Button className="submit blue-button deal-button"
                                id="approve-button"
                                onClick={() => this.updateChoice('APPROVED')}
                        >
                            {t('label.deal.approve')}
                        </Button>

                        <Button className="submit blue-button deal-button"
                                id="reject-button"
                                onClick={() => this.updateChoice('REJECTED')}
                        >
                            {t('label.deal.reject')}
                        </Button>
                    </React.Fragment>
                    : <Button className="submit blue-button deal-feedback"
                              id="feedback-button"
                              onClick={() => this.setState({feedBackForm: true})}>
                        {t('label.button.feedback')}
                    </Button>
                }
            </div>
        </div>
    };

    render() {
        const {t} = this.props;
        return <div className="white-div white-div-deal">
            {this.getForm()}
            <form className="form" id="deal-form">
                <div>
                    <Message message={this.state.message}/>
                </div>
                <div className="header-white-div">
                    <ul>
                        <li>
                            <Input
                                id='productName'
                                label={t('label.deal.productName')}
                                value={t(`label.${this.state.deal.productName}.name`)}
                                disabled={true}
                            />
                        </li>
                        <li>
                            <Input
                                id='categoryName'
                                label={t('label.deal.categoryName')}
                                value={t(`label.${this.state.deal.categoryName}.name`)}
                                disabled={true}
                            />
                        </li>
                        <li>
                            <Input
                                id='price'
                                label={t('label.deal.price')}
                                value={this.state.deal.price}
                                disabled={true}
                            />
                        </li>
                        <li>
                            <Input
                                id='volume'
                                label={t('label.deal.volume')}
                                value={this.state.deal.volume}
                                disabled={true}
                            />
                        </li>
                        <li>
                            <Input
                                id='time'
                                label={t('label.deal.time')}
                                value={this.state.deal.creationDate}
                                disabled={true}
                            />
                        </li>
                    </ul>
                </div>
                <Input
                    containerClassName='deal-status'
                    id='status'
                    label={t('label.deal.status')}
                    value={t(`label.deal.${this.state.deal.status.toLowerCase()}`)}
                    disabled={true}
                />
                <div className="body-deal-form">
                    <div>
                        <ul>
                            <li>
                                <Input
                                    id='partnerName'
                                    label={t('label.deal.partnerName')}
                                    value={this.state.deal.partnerName}
                                    disabled={true}
                                />
                                {this.getMark()}
                            </li>

                            <li>
                                <Input
                                    id='partnerComment'
                                    label={t('label.deal.partnerComment')}
                                    value={this.state.deal.partnerComment}
                                    disabled={true}
                                />
                            </li>
                            {this.state.deal.packaging
                                ? <li>
                                    <Input
                                        id='packaging'
                                        label={t('label.deal.packaging')}
                                        value={this.state.deal.packaging}
                                        disabled={true}
                                    />
                                </li>
                                : ''
                            }
                            {this.state.processing
                                ? <li>
                                    <Input
                                        id='processing'
                                        label={t('label.deal.processing')}
                                        value={this.state.deal.processing}
                                        disabled={true}
                                    />
                                </li>
                                : ''
                            }
                            <li>
                                <Input
                                    id='partnerPhone'
                                    label={t('label.deal.partnerPhone')}
                                    value={this.state.deal.partnerPhone}
                                    disabled={true}
                                />
                            </li>
                            <li>
                                <Input
                                    id='partnerRegNumber'
                                    label={t('label.deal.partnerRegNumber')}
                                    value={this.state.deal.regNumber}
                                    disabled={true}
                                />
                            </li>
                            {this.state.deal.photo
                                ? <li>
                                    <label>{t('label.deal.sellerFoto')}</label>
                                </li>
                                : ''
                            }
                        </ul>
                    </div>
                    <div>
                        <ul>
                            <li>
                                <Input
                                    id='partnerRegNumber'
                                    label={t('label.deal.partnerAddress')}
                                    value={this.state.deal.address}
                                    disabled={true}
                                />
                            </li>
                        </ul>
                        <div className="map-deal-form">
                            <div style={{bottom: 0, top: 0, right: 0, left: 0, position: 'absolute'}} id="map-mob-deal">
                                <div id="map" style={{width: '100%', height: '100%'}}>
                                    <MapContainer coords={{
                                        latitude: this.state.deal.latitude,
                                        longitude: this.state.deal.longitude
                                    }}
                                                  driveFrom={{
                                                      latitude: this.state.deal.fromLatitude,
                                                      longitude: this.state.deal.fromLongitude
                                                  }}/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {this.getButtons()}
            </form>
        </div>
    }
}

export default withTranslation()(DealDetails);