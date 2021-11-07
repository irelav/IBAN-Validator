import React, { Component } from "react";
import IbanDataService from "../services/iban.service";

export default class Iban extends Component {
  constructor(props) {
    super(props);
    this.onChangeAccount = this.onChangeAccount.bind(this);
    this.onChangeCountry = this.onChangeCountry.bind(this);
    this.getIban = this.getIban.bind(this);
    this.updateValid = this.updateValid.bind(this);
    this.updateIban = this.updateIban.bind(this);
    this.deleteIban = this.deleteIban.bind(this);

    this.state = {
      currentIban: {
        id: null,
        account: "",
        country: "",
        countryIdent: "",
        valid: false
      },
      message: ""
    };
  }

  componentDidMount() {
    this.getIban(this.props.match.params.account);
  }

  onChangeAccount(e) {
    const account = e.target.value;

    this.setState(function(prevState) {
      return {
        currentIban: {
          ...prevState.currentIban,
          account: account
        }
      };
    });
  }

  onChangeCountry(e) {
    const country = e.target.value;
    
    this.setState(prevState => ({
      currentIban: {
        ...prevState.currentIban,
        country: country
      }
    }));
  }

  getIban(account) {
    IbanDataService.get(account)
      .then(response => {
        this.setState({
          currentIban: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  updateValid(status) {
    var data = {
      id: this.state.currentIban.id,
      account: this.state.currentIban.account,
      country: this.state.currentIban.country,
      countryIdent: this.state.currentIban.countryIdent,
      valid: status
    };

    IbanDataService.update(this.state.currentIban.id, data)
      .then(response => {
        this.setState(prevState => ({
          currentIban: {
            ...prevState.currentIban,
            valid: status
          }
        }));
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  updateIban() {
    IbanDataService.update(
      this.state.currentIban.id,
      this.state.currentIban
    )
      .then(response => {
        console.log(response.data);
        this.setState({
          message: "The iban was updated successfully!"
        });
      })
      .catch(e => {
        console.log(e);
      });
  }

  deleteIban() {    
    IbanDataService.delete(this.state.currentIban.id)
      .then(response => {
        console.log(response.data);
        this.props.history.push('/ibans')
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { currentIban } = this.state;

    return (
      <div>
        {currentIban ? (
          <div className="edit-form">
            <h4>Iban</h4>
            <form>
              <div className="form-group">
                <label htmlFor="account">Account</label>
                <input
                  type="text"
                  className="form-control"
                  id="account"
                  value={currentIban.account}
                  onChange={this.onChangeAccount}
                />
              </div>
              <div className="form-group">
                <label htmlFor="country">Country</label>
                <input
                  type="text"
                  className="form-control"
                  id="country"
                  value={currentIban.country}
                  onChange={this.onChangeCountry}
                />
              </div>

              <div className="form-group">
                <label>
                  <strong>Status:</strong>
                </label>
                {currentIban.valid ? "Valid" : "Invalid"}
              </div>
            </form>

            {currentIban.valid ? (
              <button
                className="badge badge-primary mr-2"
                onClick={() => this.updateValid(false)}
              >
                UnValid
              </button>
            ) : (
              <button
                className="badge badge-primary mr-2"
                onClick={() => this.updateValid(true)}
              >
                Valid
              </button>
            )}

            <button
              className="badge badge-danger mr-2"
              onClick={this.deleteIban}
            >
              Delete
            </button>

            <button
              type="submit"
              className="badge badge-success"
              onClick={this.updateIban}
            >
              Update
            </button>
            <p>{this.state.message}</p>
          </div>
        ) : (
          <div>
            <br />
            <p>To get more info click on IBAN number</p>
          </div>
        )}
      </div>
    );
  }
}