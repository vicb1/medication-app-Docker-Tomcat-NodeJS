const Labels = {
    app_title: 'Smart Medication List', // Don't forget to change the title in index.html too.
    abbreviated_app_title: 'Medication List', // Don't forget to change the title in index.html too.
    app_description: 'An application that lists a patient\'s medication regimen.',
    home: {
        title: 'Home',
        learn_more: "Learn More",
        image_description: "Woman consulting with her doctor"
    },
    dashboard: {
        title: 'Dashboard',
        description: "<Insert dashboard content here>",
        patient_id_label: "Patient Id:",
        patient_not_found_message: "Patient not found",
        submit_button_label: "Go",
        delete_medication_button_label: "Delete Medication"
    },
    about: {
        title: 'About',
        description: 'An application that lists a patient\'s medication regimen.',
        image_description: "Man consulting with his doctor"
    },
    login: {
      title: 'Login Page',
      login_button_label: "Login",
      error_title: "Validation Error!",
      error_message: "Username and Password are required."
    },
    logout: {
      title: 'Logout'
    },
    not_found: {
      title: '404 Page Not Found',
      message: 'Go back to homepage'
    },
    signup: {
      not_implemented_message: "Signup functionality has not been implemented."
    },
    recover_password: {
      not_implemented_message: "Recover Password functionality has not been implemented."
    },
    not_implemented: {
      title: "Not Implemented",
      description: "Not Implemented"
    },
    footer: {
      text: "Ⓒ 2018 East Coasters",
      image_description: "Georgia Tech"
    },
    dosing_schedule: {
      medication_header: "Medication Name",
      route_header: "How to take it",
      breakfast_header: "Breakfast dose",
      lunch_header: "Lunch dose",
      dinner_header: "Dinner dose",
      bedtime_header: "Bedtime dose",
      routes: {
        oral_label: "swallow, or take by mouth"
      }
    },
    common: {
      patient_name: "Patient Name",
      unknown_error_message: "Unknown error"
    }
}

const LoginTypeEnum = Object.freeze({ logged_out: '', patient: 'patient' }); // https://stackoverflow.com/a/5040502/109941, 06/27/2018

export {
    Labels,
    LoginTypeEnum
};