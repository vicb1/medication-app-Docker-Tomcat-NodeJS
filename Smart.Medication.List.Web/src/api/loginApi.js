import LoginModel from '../models/Login';
import delay from './delay'; // TODO: Remove

class LoginApi {
    static login(loginModel) {
        if (!(loginModel instanceof LoginModel)) {
            throw 'payload is not of type LoginModel.';
        }
        return new Promise((resolve/*, reject*/) => { // TODO: Implement
          setTimeout(() => {
              // Simulate server-side validation
              resolve(loginModel);
          }, delay);
      });
    }

    static logout() {
        return new Promise((resolve/*, reject*/) => { // TODO: Implement
            setTimeout(() => {
                // Simulate server-side validation
                resolve();
            }, delay);
        });
    }
}

export default LoginApi;