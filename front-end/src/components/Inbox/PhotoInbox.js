import React, { useEffect, useState } from "react";

export default function PhotoInbox(){
    const [itemRequests, setItemRequests] = useState({})
    const [sessionRequests, setSessionRequests] = useState({})

    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        };

        fetch(`/api/users/${localStorage.getItem('email')}/receivedItemRequests`,requestOptions)
            .then((res) => res.json())
            .then((res) => {
                setItemRequests(res)
            }).catch(error => {console.error(error)})
    },[])

    const acceptRequest = (id) => {
        const requestOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        };
        fetch(`/api/requests/item/accept/${id}`,requestOptions)
        .then((res) => res.json())
        .then((res) => {
            console.log(res)
        }).catch(error => {console.error(error)})
    }
    
    const denyRequest = (id) => {
        const requestOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        };
        fetch(`/api/requests/item/deny/${id}`,requestOptions)
        .then((res) => res.json())
        .then((res) => {
            console.log(res)
        }).catch(error => {console.error(error)})
    }
    const style = {
        backgroundColor:'lightgray',fontWeight:'bold',borderWidth:'0ch',height:'30px'
    }
    return(
        <div>

        <h1>Item Requests</h1>
        <div>
            <h1>Pending</h1>
            {Array.from(itemRequests).filter(request => request['status'] === 'Pending').map(request => 
                             <div className="request">
                             <div>
                                 <div className="tags">
                                     <span className="author">By: {request['from']};  Status:{request['status']}</span>
                                     <span className="author">On: {request['created']}</span>
             
                                 </div>
                                 <div className="description">
                                     <p>{request['description']}</p>
                                 </div>
                             </div>
                             <img src= {`/api/uploads/${request['img']}`}/>
                             <button onClick={() => acceptRequest(request['id'])} style={style}>Accept</button>
                             <button onClick={() => denyRequest(request['id'])} style={style}>Deny</button>
                         </div>
            )}
            <h1>Accepted</h1>
            {Array.from(itemRequests).filter(request => request['status'] === 'Accepted').map(request => 
                             <div className="request">
                             <div>
                                 <div className="tags">
                                     <span className="author">By: {request['from']};  Status:{request['status']}</span>
                                     <span className="author">On: {request['created']}</span>
             
                                 </div>
                                 <div className="description">
                                     <p>{request['description']}</p>
                                 </div>
                             </div>
                             <img src= {`/api/uploads/${request['img']}`}/>
                         </div>
            )}
        </div>
        <h1>Photo Requests</h1>
        <div>
        {Array.from(sessionRequests).map( request =>
                    <div id={request['id']} className="request">
                    <div>
                        <div>
                            <span className="author">Date: {request['date']} </span>
                        </div>
                        <div>
                            <span className="author">At: {request['location']} </span>
                        </div>
                        <div>
                            <span className="author">People: {request['people']}</span>
                        </div>
                        <div>
                            <span className="author">Status: {request['status']}</span>
                        </div>
                        <div className="description">
                            <p>{request['description']}</p>
                        </div>
                    </div>
                    <button onClick={() => acceptRequest(request['id'])} style={style}>Accept</button>
                    <button onClick={() => denyRequest(request['id'])} style={style}>Deny</button>
                </div>

            )}
        </div>
        </div>
    )
}