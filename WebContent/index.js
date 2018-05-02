function submitExpenseForm(formSubmitEvent) {
    console.log("submit expense form");

    formSubmitEvent.preventDefault();

    jQuery.post(
        "api/add-expense",
        // Serialize the login form to the data sent by POST request
        jQuery("#expense_form").serialize());
}

jQuery("#expense_form").submit((event) => submitExpenseForm(event));