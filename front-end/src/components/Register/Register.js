import React from "react"
import "./Register.css"

function registerUser(){
    const passwd_reg=/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/
    const username=document.getElementById('username').value;
    const password=document.getElementById('password').value;
    const email=document.getElementById('email').value;
    const email_reg=/^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
    let isError=false;

    if (username=="" || username.length<3 || username.length>20) {
        document.getElementById("username-error").style.display="inline-block"
        document.getElementById('username').borderColor="#B0706D"
        isError=true;
        }
    else{
        document.getElementById('username-error').style.display="none"
        document.getElementById('username').borderColor="#C3CDC0"
    }

    if(email_reg.test(email)==false){
        document.getElementById("email-error").style.display="inline-block"
        document.getElementById('email-error').innerHTML="Invalid Email"
        document.getElementById('email').borderColor="#B0706D"
        isError=true;
       }
    else{
        document.getElementById("email-error").style.display="none"
        document.getElementById('email').borderColor="#C3CDC0"
    }

    if(passwd_reg.test(password)==false){
        document.getElementById("password-error").style.display="inline-block"
        document.getElementById('password-error').innerHTML="Password must contain at least one small letter, large letter, digit and be 8-20 character long"
        document.getElementById('password').borderColor="#B0706D"
        isError=true;
    }
    else{
        document.getElementById("password-error").style.display="none"
        document.getElementById('password').borderColor="#C3CDC0"
    }

    if (!isError){
        const user = {"id":email, "password": password, "name": username, type: "USER"}
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user) 
        }; 
        fetch('/api/users/register', requestOptions).then(res => res.json())
        .then(data => 
           { 
            localStorage.setItem("email",email)
            localStorage.setItem("type","USER")
            localStorage.setItem("token",data["token"])
            window.location.reload()
            }
            
            ).catch(error => console.log(error));
    }



}
export default function Register(){
    return (
        <div className="RegistrationForm" action="registerUser.php" method="post">
        <h1 className="headers">Registration</h1>
                <span className="field-name">Email</span>
                <input className="field-input" id="email" name="email" type="text"></input>
                <span id="email-error" className="error">Invalid email</span>
                <span className="field-name">User Name</span>
                <input className="field-input" id="username" name="username" type="text"></input>
                <span id="username-error" className="error">Username must contain 3-20 characters</span>
                <span className="field-name">Password</span>
                <input id="password" className="field-input" name="password" type="password"></input>
                <span id="password-error" className="error">Password must contain at least one small letter, large letter, digit and be 8-20 character long</span>
                <button onClick={registerUser} className="submit-btn" name="submit" value="Submit">Submit</button> 
        </div>
    )
}