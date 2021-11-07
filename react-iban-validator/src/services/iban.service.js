import http from "../http-common";

class IbanDataService {
  getAll() {
    return http.get("/ibans?valid=all");
  }

  get(account) {
    return http.get(`/ibans/${account}`);
  }

  create(data) {
    return http.post("/ibans", data);
  }

  update(id, data) {
    return http.put(`/ibans/${id}`, data);
  }

  delete(id) {
    return http.delete(`/ibans/${id}`);
  }

  deleteAll() {
    return http.delete(`/ibans`);
  }

  findByValid(valid) {
    return http.get(`/ibans?valid=${valid}`);
  }
}

export default new IbanDataService();