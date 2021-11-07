import React, { Component } from "react";
import IbanDataService from "../services/iban.service";

export default class IbansList extends Component {
  constructor(props) {
    super(props);
    this.onChangeSearchValid = this.onChangeSearchValid.bind(this);
    this.retrieveIbans = this.retrieveIbans.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveIban = this.setActiveIban.bind(this);
    this.removeAllIbans = this.removeAllIbans.bind(this);
    this.searchValid = this.searchValid.bind(this);

    this.state = {
      ibans: [],
      currentIban: null,
      currentIndex: -1,
      searchValid: ""
    };
  }

  componentDidMount() {
    this.retrieveIbans();
  }

  onChangeSearchValid(e) {
    const searchValid = e.target.value;

    this.setState({
      searchValid: searchValid
    });
  }

  retrieveIbans() {
    IbanDataService.getAll()
      .then(response => {
        this.setState({
          ibans: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  refreshList() {
    this.retrieveIbans();
    this.setState({
      currentIban: null,
      currentIndex: -1
    });
  }

  setActiveIban(iban, index) {
    this.setState({
      currentIban: iban,
      currentIndex: index
    });
  }

  removeAllIbans() {
    IbanDataService.deleteAll()
      .then(response => {
        console.log(response.data);
        this.refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  }

  searchValid() {
    IbanDataService.findByValid(this.state.searchValid)
      .then(response => {
        this.setState({
          ibans: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { searchValid, ibans, currentIban, currentIndex } = this.state;

    return (
      <div className="list row">
        <div className="col-md-8">
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Search by valid type: valid / invalid / all"
              value={searchValid}
              onChange={this.onChangeSearchValid}
            />
            <div className="input-group-append">
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={this.searchValid}
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <h4>List of IBANs</h4>
          <ul className="list-group">
            {ibans &&
              ibans.map((iban, index) => (
                <li
                  className={
                    "list-group-item " +
                    (index === currentIndex ? "active" : "")
                  }
                  onClick={() => this.setActiveIban(iban, index)}
                  key={index}
                >
                  {iban.account}
                </li>
              ))}
          </ul>
          <button
            className="m-3 btn btn-sm btn-danger"
            onClick={this.removeAllIbans}
          >
            Remove All
          </button>
        </div>
        <div className="col-md-6">
          {currentIban ? (
            <div>
              <h4>IBAN information:</h4>
              <div>
                <label>
                  <strong>Account:</strong>
                </label>{" "}
                {currentIban.account}
              </div>
              <div>
                <label>
                  <strong>Country:</strong>
                </label>{" "}
                {currentIban.country}
              </div>
              <div>
                <label>
                  <strong>Country Identy code:</strong>
                </label>{" "}
                {currentIban.countryIdent}
              </div>
              <div>
                <label>
                  <strong>Status:</strong>
                </label>{" "}
                {currentIban.valid ? "Valid" : "Invalid"}
              </div>
            </div>
          ) : (
            <div>
              <p>Click on IBAN number</p>
            </div>
          )}
        </div>
      </div>
    );
  }
}