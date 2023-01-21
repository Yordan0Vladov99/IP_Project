import React from "react";


export default function PhotoSessionForm(){

    const requestSession = () => {
        const location=document.getElementById('location').value;
        const time=document.getElementById('time').value;
        const date=document.getElementById('date').value;
        const quantity=document.getElementById('quantity').value;
        const description=document.getElementById('description').value;
        let isError = false
        
        if (location=="" || location.length<10 || location.length>50) {
            document.getElementById("location-error").style.display="inline-block"
            document.getElementById('location').borderColor="#B0706D"
            isError=true;
            }
        else{
            document.getElementById('location-error').style.display="none"
            document.getElementById('location').borderColor="#C3CDC0"
        }

        let today = new Date();
        const dd = String(today.getDate()).padStart(2, '0');
        const mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        const yyyy = today.getFullYear();


        if (date=="" | date < today) {
            document.getElementById("date-error").style.display="inline-block"
            document.getElementById('date').borderColor="#B0706D"
            isError=true;
        }else{
            document.getElementById('date-error').style.display="none"
            document.getElementById('date').borderColor="#C3CDC0"
        }

        if (time=="") {
            document.getElementById("time-error").style.display="inline-block"
            document.getElementById('time').borderColor="#B0706D"
            isError=true;
        }else{
            document.getElementById('time-error').style.display="none"
            document.getElementById('time').borderColor="#C3CDC0"
        }
    
        if (quantity=="" | quantity<1 ) {
            document.getElementById("quantity-error").style.display="inline-block"
            document.getElementById('quantity').borderColor="#B0706D"
            isError=true;
        }
        else{
            document.getElementById('quantity-error').style.display="none"
            document.getElementById('quantity').borderColor="#C3CDC0"
        }
    
        if (description=="" || description.length<10 ||  description.length > 150 ) {
            document.getElementById("description-error").style.display="inline-block"
            document.getElementById('description').borderColor="#B0706D"
            isError=true;
        }
        else{
            document.getElementById('description-error').style.display="none"
            document.getElementById('description').borderColor="#C3CDC0"
        }

        if(!isError){
            const request = {
                "sender" : localStorage.getItem('email'),
                "people" : quantity,
                "date" : date,
                "time" : time,
                "location" : location,
                "description" : description
            }

            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization' : `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(request)
            }; 
            
            fetch(`/api/requests/session/create`,requestOptions)
            .then(res => res.json())
            .then( res => {
                console.log(res)
            }).catch(error => console.error(error))
        }
    

    }
    return(
        <div className="LoginForm">
            <h1 className="headers">Photo Session Form</h1>
            <div className="container">
            <div className="one-line">
                <label className="field-name" for="location">Location</label>
                <input className="field-input" id="location" name="location" type="text"></input>
            </div>
            <span id="location-error" className="error">Location must contain 10-45 characters</span>

            <div className="one-line">
        <label className="field-name" for="date">Date</label>
        <input id="date" className="field-input" name="date" type="date"></input>
        </div>
        <span id="date-error" className="error">Field mustn't be empty</span>
        
        <div className="one-line">
        <label className="field-name" for="time">Time</label>
        <input id="time" className="field-input" name="time" type="time"></input>
        </div>
        <span id="time-error" className="error">Field mustn't be empty and must be a future date</span>
            
        <div className="one-line">
        <label className="field-name" for="quantity">Number of people</label>
        <input id="quantity" className="field-input" name="quantity" type="number" min="1"></input>
        </div>
        <span id="quantity-error" className="error">Number must be a positive integer</span>

        <div className="one-line">
        <label className="field-name" for="description">Description</label>
        <textarea className="field-input" id="description" name="description" rows="5"/>
        </div>
        <span id="description-error" className="error">Description must contain 10-150 characters</span>
            </div>
    <button onClick={requestSession} className="submit-btn" name="submit" value="Submit">Submit</button>
        
        </div>
    )
}