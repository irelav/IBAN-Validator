import React, { Component } from "react";
import IbanDataService from "../services/iban.service";

export default class AddIban extends Component {
  constructor(props) {
    super(props);
    this.onChangeAccount = this.onChangeAccount.bind(this);
    this.onChangeCountry = this.onChangeCountry.bind(this);
    this.saveIban = this.saveIban.bind(this);
    this.newIban = this.newIban.bind(this);

    this.state = {
      id: null,
      account: "",
      country: "", 
      valid: false,

      submitted: false
    };
  }

  onChangeAccount(e) {
    this.setState({
      account: e.target.value
    });
  }

  onChangeCountry(e) {
    this.setState({
      country: e.target.value
    });
  }

  saveIban() {
    var data = {
      account: this.state.account
    };

    IbanDataService.create(data)
      .then(response => {
        this.setState({
          id: response.data.id,
          account: response.data.account,
          country: response.data.country,
          valid: response.data.valid,

          submitted: true
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  newIban() {
    this.setState({
      id: null,
      account: "",
      country: "",
      valid: false,

      submitted: false
    });
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={this.newIban}>
              Add
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="account">Account</label>
              <input
                type="text"
                className="form-control"
                id="account"
                required
                value={this.state.account}
                onChange={this.onChangeAccount}
                name="account"
              />
            </div>
            <button onClick={this.saveIban} className="btn btn-success">
              Submit
            </button>
          </div>
        )}
      </div>
    );
  }
}