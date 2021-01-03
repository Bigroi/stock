import React from 'react';
import {withTranslation} from 'react-i18next';
import Button from '../../components/input/Button';
import TextArea from '../../components/input/TextArea';
import ApiUrls from '../../util/ApiUrls';
import Request from '../../util/Request';

class CompanyFeedBackForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isNew: true,
            mark: 5,
            preMark: null,
            comment: ''
        }
    }

    componentDidMount() {
        Request.doGet(ApiUrls.DEAL_FEED_BACK.replace('{id}', this.props.dealId))
            .then(async response => {
                if (response.ok) {
                    this.setState({...JSON.parse(await response.text()), isNew: false});
                    return null;
                } else {
                    return null;
                }
            });
    }

    getStarClass = (mark) => {
        return (this.state.preMark || this.state.mark) >= mark
            ? 'reviewStarsFeed-start-checked'
            : 'reviewStarsFeed-start-unchecked'
    };

    renderMark = () => {
        return <div className='reviewStarsFeed'>
            {[5, 4, 3, 2, 1]
                .map(mark =>
                    <label
                        key={`star-${mark}`}
                        className={this.getStarClass(mark)}
                        onClick={() => this.setState({mark: mark})}
                        onMouseEnter={() => this.setState({preMark: mark})}
                        onMouseLeave={() => this.setState({preMark: null})}
                    />
                )}
        </div>
    };

    submitFeedBack = () => {
        const method = this.state.isNew
            ? Request.doPost
            : Request.doPut;
        method(ApiUrls.DEAL_FEED_BACK.replace('{id}', this.props.dealId),
            null,
            {
                mark: this.state.mark,
                comment: this.state.comment
            }
        ).then(response => {
            if (response.ok) {
                this.props.onClose();
            }
        });
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form'>
            <h3>{t('label.deal.feedback')}</h3>
            <div className='flex-input'>
                {this.renderMark()}
                <TextArea
                    id='Comment'
                    label={t('label.comment.feedback')}
                    name='comment'
                    className='comment'
                    maxLength={500}
                    value={this.state.comment}
                    onChange={(newValue => this.setState({comment: newValue}))}
                />
                <div id='form-list'>
                    <Button className='submit button' id='save' onClick={this.submitFeedBack}>
                        {t('label.button.save')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(CompanyFeedBackForm);