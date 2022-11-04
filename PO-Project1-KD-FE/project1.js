const url = "http://localhost:8080"; //putting our base URL in a variable for cleaner fetch code
//this will be used in our fetch requests to send HTTP reuqests to our javalin handlers

//I like to add all my event listeners at the top
document.getElementById("getReimbursementsButton").onclick = getReimbursements; //this button will GET all reimbursements
document.getElementById("submitReimbursementButton").onclick = submitReimbursement; //this button will POST a new reimbursement
document.getElementById("submitUserButton").onclick = createUser; //this button will POST a new user
document.getElementById("logInButton").onclick = logInUser; //this button will log in using POST

//I like to add all my functinos at the bottom--------------------------------

//This function is an async function which uses a fetch request to get data from our API
//async functions return promise objects, which PROMISE that some HTTP response will be returned.
async function getReimbursements(){

    //a variable that holds the result of a fetch request for Reimbursement data from the API
    let response = await fetch(url + "/getReimbursements") 

    //async & await? 
    //async makes a function asynchronous 
    //await pauses the function until the promise object is returned 

    console.log(response) //print out the response
    
    //take the HTTP response and turn it into a readable format (from JSON to JS)
    let data = await response.json();

    console.log(data) //now, we can print out the javascript array and use it to render some data

    //if the HTTP Response status code is 200 (OK)...
    if(response.status === 200){

        //For every reimbursement object we get back from our fetch, we want to render it in the table
        //"reimbursement" is simply the variable name we're giving to each of the Array's elements
        for(let reimbursement of data){

            //create a new table body row (tr) for the incoming reimbursement
            let row = document.createElement("tr");

            //create a table data cell (td) for each of the reimbursement's fields
            let cell1 = document.createElement("td");
            //fill the cell with the appropriate data
            cell1.innerHTML = reimbursement.user;
            //add the new cell to the row
            row.appendChild(cell1)

            let cell2 = document.createElement("td");
            cell2.innerHTML = reimbursement.amount;
            row.appendChild(cell2)

            let cell3 = document.createElement("td");
            cell3.innerHTML = reimbursement.description;
            row.appendChild(cell3)

            let cell4 = document.createElement("td");
            cell4.innerHTML = reimbursement.status;
            row.appendChild(cell4)

            //finally, we need to actually APPEND THE ROW to the table body
            //a new row will be appended FOR EVERY REIMBURSEMENT that got returned in the fetch
            document.getElementById("reimbursementsBody").appendChild(row);
        }

    } 

}//end of get all reimbursements function


//This function will take in user input, and create a reimbursement object to send in a POST request
async function submitReimbursement(){

    //gather the user's input from the submit reimbursement input boxes
    //when the submit button is clicked, these variables will be populated
    let userInput = document.getElementById("user").value; //we can gather inputs with .value 
    let amountInput = document.getElementById("amount").value;
    let descriptionInput = document.getElementById("description").value;

    //now we have the inputs, but we need to put them in an object to send in the fetch 
    let newReimbursement = {
        reimbursement_id: 0, //this will be changed by the DAO
        user: userInput,
        amount: amountInput,
        description: descriptionInput,
        status: 0 //this will be changed when the reimbursement is returned
    } //NOTE: the variable names MUST match the variable names in your API.

    //now that we have a Reimbursement object, we're ready to send a POST request

    //any fetch request besides GET requires a few more parameters
        //It will include: URL, and a config object 
    let response = await fetch(url + "/updateReimbursements",{
        method: "POST", //send a POST requests (would be GET if we didn't specify)
        body: JSON.stringify(newReimbursement) //turn the newReimbursement into JSON, and send it in the request body
    });

    //log the response just so we can see what comes back
    console.log(response)

    //popup to tell the user their new reimbursement was created
    if(response.status === 201){
        alert("New Reimbursement Created: By: " + userInput + " amount: " + amountInput + " description: " + descriptionInput)
    }

} //end of submit reimbursement function


async function createUser(){


    let makeUsername = document.getElementById("createUsername").value;
    let makePassword = document.getElementById("createPassword").value;

    let newUser = {
        user_id: 0, 
        username: makeUsername,
        password: makePassword,
        isAdmin: isAdminInput,
        status: 0
    } 

    let response = await fetch(url + "/createUser",{
        method: "POST", //send a POST requests (would be GET if we didn't specify)
        body: JSON.stringify(newUser) //turn the newReimbursement into JSON, and send it in the request body
    });

    //log the response just so we can see what comes back
    console.log(response)

    //popup to tell the user their new reimbursement was created
    if(response.status === 201){
        alert("New User Created: Username: " + makeUsername + " Password: " + makePassword)
    }

} //end of create a new user function


async function logInUser(){

    //gather the user's input from the submit reimbursement input boxes
    //when the submit button is clicked, these variables will be populated
    let logInUsername = document.getElementById("logInUsername").value; //we can gather inputs with .value 
    let logInPassword = document.getElementById("logInPassword").value;

    //now we have the inputs, but we need to put them in an object to send in the fetch 
    let logInUser = {
        user_id: 0, 
        username: logInUsername,
        password: logInPassword,
        isAdmin: 0,
    }  


    let response = await fetch(url + "/login",{
        method: "POST", //send a POST requests (would be GET if we didn't specify)
        body: JSON.stringify(logInUser)
    });

    console.log(response)


    if(response.status === 201){
        alert("User Logged In: Username: " + logInUsername + " Password: " + logInPassword)
    }

} //end of log in user function