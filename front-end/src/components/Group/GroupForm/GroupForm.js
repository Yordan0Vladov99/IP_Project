import React, { Component } from "react";


function createGroup(groupId){
    const title = document.getElementById('group-title').value
    let isError=false;
    const parentId = groupId ? groupId : 0;

    if (title == "" || title.length >= 50) {
        document.getElementById("title-error").style.display="inline-block"
        document.getElementById('group-title').borderColor="#B0706D"
        isError=true
    }
    else {
        document.getElementById('title-error').style.display="none"
        document.getElementById('group-title').borderColor="#C3CDC0"
    }


    if(!isError){
      const email = localStorage.getItem('email')
      const group = {"user":email, "group":title, "parent" : parentId}
      console.log(group)
      const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : `Bearer ${localStorage.getItem('token')}`,
    },
    body: JSON.stringify(group)
  }
  
  fetch(`/api/groups/create`,requestOptions)
            .then((res => res.json()))
            .then(res => {
                console.log(res)
            }).catch(error => {console.error(error)})
      
    }
}


export default class GroupForm extends Component {
    handleClick = () => {
        this.props.toggle();
      };

      
    
      render() {
        return (
          <div className="modal">
            <div className="modal_content">
              <span className="close" onClick={this.handleClick}>
                &times;
              </span>
              <div className="LoginForm">
                    <h1 className="headers">Create Group</h1>
    
                    <span className="field-name">Group Name</span>
                    <input className="field-input" id="group-title" name="title" type="text"></input>
                    <span id="title-error" className="error"></span>


                    <button onClick={() => createGroup(this.props.groupId)} className="submit-btn" name="submit" value="Submit">Submit</button>
            </div>
            </div>
          </div>
        );
      }
  }