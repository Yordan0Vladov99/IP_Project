import React from "react"
import "./Login.css"



async function loginUser(){
    const passwd_reg=/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,50}$/
    const email=document.getElementById('email').value;
    const password=document.getElementById('password').value;
    let isError=false;

    if (email==="" || email.length<3 || email.length>50) {
    document.getElementById("email-error").style.display="inline-block"
    document.getElementById('email').borderColor="#B0706D"
    isError=true;
    }
    else{
    document.getElementById('email-error').style.display="none"
    document.getElementById('email').borderColor="#C3CDC0"
    }

    if(passwd_reg.test(password)===false){
    document.getElementById("password-error").style.display="inline-block"
    document.getElementById('password-error').innerHTML="Password must contain at least one small letter, large letter, digit and be 8-50 character long"
    document.getElementById('password').borderColor="#B0706D"
    isError=true;
   }

    if (!isError){
        const user = {"id": email, "password":password}
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user) 
        }; 
        fetch('/api/users/authenticate', requestOptions).then(res => res.json())
        .then(data => 
           { 
            console.log(data)
            localStorage.setItem("email",email)
            localStorage.setItem("type",data["userType"])
            localStorage.setItem("token",data["token"])
            window.location.reload()
            }
            
            ).catch(error => console.log(error));
    }

}

export default function Login(){
    return (
    <div className="LoginForm" >
        <h1 className="headers">Login</h1>
        <span className="field-name">Email</span>
        <input className="field-input" id="email" name="email" type="text"></input>
        <span id="email-error" className="error">email must contain 3-50 characters</span>
        <span className="field-name">Password</span>
        <input id="password" className="field-input" name="password" type="password"></input>
        <span id="password-error" className="error">Password must contain at least one small letter, large letter, digit and be 8-50 character long</span>
        <button onClick={loginUser} className="submit-btn" name="submit" value="Submit">Submit</button>
    </div>
    )
}