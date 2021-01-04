import React from 'react';
import {withTranslation} from 'react-i18next';

class Questions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tab: [
                false,
                false,
                false,
                false
            ]
        }
    }

    getQuestions = () => {
        const {t} = this.props;
        return [
            {
                question: t('label.index.header_question_one'),
                answer: t('label.index.body_answer_one')
            },
            {
                question: t('label.index.header_question_two'),
                answer: t('label.index.body_answer_two')
            },
            {
                question: t('label.index.header_question_three'),
                answer: t('label.index.body_answer_three')
            },
            {
                question: t('label.index.header_question_four'),
                answer: t('label.index.body_answer_four')
            },
        ];
    };

    changeTab = (index) => {
        const tab = this.state.tab.slice();
        tab[index] = !(tab[index]);
        this.setState({tab: tab});
    };

    render() {
        const {t} = this.props;
        return <div className='container'>
            <div>
                <span className='span_h3'>{t('label.index.frequently_asked_question')}</span>
                <p className='answer'>{t('label.index.not_found_answer')}</p>
                <p className='mail'><a href=''>info@yourtrader.eu</a></p>
            </div>
            <div>
                {this.getQuestions().map((q, index) =>
                    <div className='question-answer' key={q.question}>
                        <div>
                            <div className='header-question' onClick={() => this.changeTab(index)}>
                                {q.question}
                                <div className={`question-close ${this.state.tab[index] ? 'question-open' : ''}`}/>
                            </div>
                            {this.state.tab[index]
                                ? <div className='body-answer'>{q.answer}</div>
                                : ''
                            }
                        </div>
                    </div>
                )}
            </div>
        </div>;
    }
}

export default withTranslation()(Questions);