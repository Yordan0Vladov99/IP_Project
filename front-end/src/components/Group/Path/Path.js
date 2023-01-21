import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Path(){
    const [path, setPath] = useState({})
    const {id} = useParams();
    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        }; 

    fetch(`/api/groups/${id}/path`,requestOptions)
            .then((res => res.json()))
            .then(res => {
                setPath(res)
            }).catch(error => {console.error(error)})
    }
    ,[])


        return(
            <ul>
               {
               Array.from(path).map(
                   group => <li key={group['id']}><a href={`/groups/${group['id']}`}>{group['name']}</a></li>
               )
               }
           </ul>
           )
}
