import React from 'react';
import i18n from 'i18next';
import DatePicker, {registerLocale} from 'react-datepicker';
import pl from 'date-fns/locale/PL';
import en from 'date-fns/locale/en-US';
import ru from 'date-fns/locale/ru';
import './Calendar.css';
import 'react-datepicker/dist/react-datepicker.css';

registerLocale('PL', pl);
registerLocale('EN', en);
registerLocale('RU', ru);

/**
 * id: String
 * label: String
 * value: date
 * onChange: function
 */
export default class Calendar extends React.Component {

    render() {
        return (
            <div>
                <label htmlFor={`for-${this.props.id}`}>{this.props.label}</label>
                <DatePicker
                    className={this.props.error ? 'input-error' : ''}
                    selected={new Date(this.props.value)}
                    onChange={this.props.onChange}
                    dateFormat='dd.MM.yyyy'
                    id={this.props.id}
                    locale={i18n.language}
                />
            </div>
        );
    }
}