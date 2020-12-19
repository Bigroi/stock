import React from 'react';
import {withTranslation} from 'react-i18next';

/**
 * pageSize: number
 * data: [[any]]
 * header: [{name: String, weight: number}]
 */
class Table extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            pageNumber: 1,
            search: ''
        }
    }

    getPageSize = () => {
        return this.props.pageSize || 10;
    };

    getPaginator = () => {
        if (this.props.data.length > this.getPageSize) {
            return <div className="dataTables_paginate paging_simple_numbers" id="main-table_paginate" style={{display: 'block'}}>
                <a className="paginate_button previous disabled"
                   id="main-table_previous"
                   onClick={() => {
                       if (this.state.pageNumber > 1) {
                           this.setState({pageNumber: this.state.pageNumber + 1});
                       }
                   }}
                > </a>
                <span><a className="paginate_button current">{this.state.pageNumber}</a></span>
                <a className="paginate_button next disabled"
                   id="main-table_next"
                   onClick={() => {
                       if (this.state.pageNumber < this.props.data.length / 10) {
                           this.setState({pageNumber: this.state.pageNumber - 1});
                       }
                   }}
                > </a>
            </div>
        }
    };

    onSearchChange = (e) => {
        this.setState({
            search: e.currentTarget.value,
            pageNumber: 1
        })
    };

    filterData = () => {
        return this.props.data
            .filter(obj => Object.values(obj).find(field =>
                typeof field === 'string' && field.indexOf(this.state.search) >= 0));
    };

    render() {
        const {t} = this.props;
        const data = this.filterData().slice(
            (this.state.pageNumber - 1) * this.getPageSize(),
            this.state.pageNumber * this.getPageSize()
            );

        return <div id="table-container">
            <div id="main-table_wrapper" className="dataTables_wrapper no-footer">
                <div id="main-table_filter" className="dataTables_filter">
                    <label>
                        <input
                            type="search"
                            placeholder={t('label.table.searchPlaceholder')}
                            onChange={this.onSearchChange}
                            value={this.state.search}
                        />
                    </label>
                </div>
                <table id="main-table" className="display responsive nowrap dataTable no-footer dtr-inline collapsed"
                       style={{width: '100%'}}>
                    <thead>
                    <tr role="row">
                        {this.props.header.map(h =>
                            <th className={`sorting_disabled column-weight-${h.weight}`}>{h.name}</th>
                        )}
                    </tr>
                    </thead>
                    <tbody>
                    {data.map(d =>
                        <tr role="row" className="odd inactive-row">
                            {d.map((cell, index) =>
                                <td className={`column-weight-${this.props.header[index].weight}`}>{cell}</td>
                            )}
                        </tr>
                    )}
                    </tbody>
                </table>

            </div>
        </div>
    }
}

export default withTranslation()(Table);