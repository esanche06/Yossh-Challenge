function handleResult(resultData) {
    let starInfoElement = jQuery("#expenses_table_info");

    console.log(JSON.stringify(resultData));
    
    let expensesTableBodyElement = jQuery("#expenses_table_body");

    for (let i = 0; i < resultData.length; i++) {
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<td>" + resultData[i]["dateOfExpense"] + "</td>";
        rowHTML += "<td>" + resultData[i]["value"] + "</td>";
        rowHTML += "<td>" + resultData[i]["reason"] + "</td>";
        rowHTML += "</tr>";

        expensesTableBodyElement.append(rowHTML);
    }
}

jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "api/show-expenses", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleResult(resultData) // Setting callback function to handle data returned successfully by the SingleStarServlet
});
