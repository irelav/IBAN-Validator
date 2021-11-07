import React, { Component } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AddIban from "./components/add-iban.component";
import IbansList from "./components/iban-list.component";

class App extends Component {
  render() {
    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/ibans"} className="navbar-brand">
            IBAN Validation
          </Link>
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/ibans"} className="nav-link">
                List of IBANs
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/add"} className="nav-link">
                Add an IBAN
              </Link>
            </li>
          </div>
        </nav>

        <div className="container mt-3">
          <Routes>
            <Route exact path="/ibans" element={<IbansList />} />
            <Route path="/add" element={<AddIban />} />
          </Routes>
        </div>
      </div>
    );
  }
}

export default App;