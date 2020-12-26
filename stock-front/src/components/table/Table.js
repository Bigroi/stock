import React from 'react';
import {withTranslation} from 'react-i18next';
import './Table.css';
import Message from "../message/Message";

/**
 * pageSize: number
 * data: [[any]]
 * header: [{name: String, weight: number}]
 * message: {type: String, value:String}
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

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.data.length < this.props.data.length) {
            this.setState({pageNumber: 1, search: ''})
        }
    }

    getPaginator = (data) => {
        console.log(data);
        if (data.length > this.getPageSize()) {
            return <div className="dataTables_paginate paging_simple_numbers" id="main-table_paginate"
                        style={{display: 'block'}}>
                <a className="paginate_button previous disabled"
                   id="main-table_previous"
                   onClick={() => {
                       if (this.state.pageNumber > 1) {
                           this.setState({pageNumber: this.state.pageNumber - 1});
                       }
                   }}
                > </a>
                <span><a className="paginate_button current">{this.state.pageNumber}</a></span>
                <a className="paginate_button next disabled"
                   id="main-table_next"
                   onClick={() => {
                       if (this.state.pageNumber < data.length / 10) {
                           this.setState({pageNumber: this.state.pageNumber + 1});
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
                typeof field === 'string' && field.toLowerCase().indexOf(this.state.search.toLowerCase()) >= 0));
    };

    getTableRows = (data) => {
        const {t} = this.props;
        if (data.length === 0) {
            return <tr>
                <td valign="top" colSpan={this.props.header.length} className="dataTables_empty">
                    {t('label.table.emptyTable')}
                </td>
            </tr>
        } else {
            return data.map((d, rowIndex) =>
                <tr role="row" className="odd inactive-row" key={`table-row-${rowIndex}`}>
                    {d.map((cell, cellIndex) =>
                        <td className={`column-weight-${this.props.header[cellIndex].weight}`}
                            key={`table-row-${rowIndex}-cell-${cellIndex}`}
                        >
                            {cell}
                        </td>
                    )}
                </tr>
            );
        }
    };

    getHeader = () => {
        if (this.props.message) {
            return <th colSpan={this.props.header.length} className='table-message-header'>
                <Message message={this.props.message} className='table-message'/>
            </th>
        } else {
            return this.props.header.map((h, index) =>
                <th className={`sorting_disabled column-weight-${h.weight}`} key={`table-head-${index}`}>
                    {h.name}
                </th>
            );
        }
    };

    render() {
        const {t} = this.props;
        const filteredData = this.filterData();
        const data = filteredData.slice(
            (this.state.pageNumber - 1) * this.getPageSize(),
            this.state.pageNumber * this.getPageSize()
        );

        return <div id="table-container">
            <div id="main-table_wrapper" className="dataTables_wrapper no-footer">
                <div id="main-table_filter" className="dataTables_filter">
                    <label>
                        <input
                            type="text"
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
                        {this.getHeader()}
                    </tr>
                    </thead>
                    <tbody>
                    {this.getTableRows(data)}
                    </tbody>
                </table>
                {this.getPaginator(filteredData)}
            </div>
        </div>
    }
}

export default withTranslation()(Table);