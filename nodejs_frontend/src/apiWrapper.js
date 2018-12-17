/* This API wrapper is useful because it:
   1. Centralizes our Axios default configuration.
   2. Abstracts away the logic for determining the baseURL.
   3. Provides a clear, easily consumable list of JavaScript functions
      for interacting with the API. This keeps API calls short and consistent.
*/
// c/o Cory House's GitHub Repository, 07/04/2018, https://gist.githubusercontent.com/coryhouse/09fb49dd0c13ca20cca6cc0fe2438f3e/raw/ceb68883b2412e7037457ebd4dc49ec1e6b2857f/userApi.js
import axios from 'axios';

let api = null;

function getInitializedApi() {
  if (api) return api; // return initialized api if already initialized.
  return (api = axios.create({
    baseURL: getBaseUrl(),
    responseType: 'json',
    withCredentials: false
  }));
}

// Helper functions
function getBaseUrl() {
    return process.env.__API_URL__;
}

function get(url) {
  return getInitializedApi().get(url);
}

function post(url, data) {
  return getInitializedApi().post(url, data);
}

function delete_command(url, data) {
  return getInitializedApi().delete(url, {params: data}); // https://github.com/axios/axios/issues/736, 07/15/2018
}

/*
// FOR EXAMPLE ONLY. Functions like this belong in their own Api file.
// Public functions
// Note how short these are due to the centralized config and helpers above. 😎
export function getUserById(id) {
  return get(`user/${id}`);
}

export function saveUser(user) {
  return post(`user/${user.id}`, {user: user});
}
*/

export {
    get,
    post,
    delete_command
};
