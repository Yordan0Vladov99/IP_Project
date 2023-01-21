import React, { useEffect, useState } from "react";
import './Cart.css'

export default function Cart(){

    const [cartItems, setCartItems] = useState({})
    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        }; 
        fetch(`/api/users/${localStorage.getItem('email')}/cartItems`,requestOptions)
        .then(res => res.json())
        .then( res => {
            setCartItems(res)
        }).catch(error => console.error(error))
    },[])

    const sendItem = (id) => {
        const user = localStorage.getItem('email')
        let isError = false
        const quantity = document.getElementById(`${id}-quantity`).value;
        if (quantity == "" || quantity < 1) {
            document.getElementById(`${id}-quantity-error`).innerHTML = "Quantity must be a positive integer";
            isError = true
        } else {
            document.getElementById(`${id}-quantity-error`).innerHTML = ""
        }

        if (!isError){
            const type = document.getElementById(`${id}-type`).innerHTML;
            const price = document.getElementById(`${id}-price`).innerHTML
            const quantity =  document.getElementById(`${id}-quantity`).value;
            let image = document.getElementById(`${id}-image`).alt
            image = image.substr(0,image.lastIndexOf('.')) || image
            let total = price * quantity;
            if (quantity >= 1000) {
                total *= 0.7;
            } else if (quantity >= 100) {
                total *= 0.8;
            } else if (quantity >= 10) {
                total *= 0.9;
            }
            const description = {
                "quantity" : quantity,
                "total price" : total,
                "type" :  type,
            }
            const request = {
                "user" : user,
                "img" : image,
                "description" : JSON.stringify(description)
            }

            console.log(request)

            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization' : `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(request)
            }; 
            
            fetch(`/api/requests/item/create`,requestOptions)
            .then(res => res.json())
            .then( res => {
                console.log(res)
                removeItem(id)
            }).catch(error => console.error(error))

        }

    }
    const removeItem = (id) => {
        const requestOptions = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization' : `Bearer ${localStorage.getItem('token')}`
                },
            }; 
            
            fetch(`/api/items/cartItems/${id}`,requestOptions)
            .then(res => res.json())
            .then( res => {
                console.log(res)
            }).catch(error => console.error(error))
    }
    
    let style = {
        backgroundColor: "lightgray",
        fontWeight: "bold",
        borderWidth: "0ch",
    }

    return(
        <div>
            {Array.from(cartItems).map(
                item => 
                <div className="cart-item">
                    <div className="description">
                    <p>Type: <span id={`${item.id}-type`} type>{item.type}</span></p>
                    <p>Price: <span id={`${item.id}-price`} type>{item.price}</span></p>
                    <span>Quantity:</span>
                    <input type="number" min="1" id={`${item.id}-quantity`}></input>
                    <button onClick={() => sendItem(item.id)} style={style}>Send</button>
                    <button onClick={() => removeItem(item.id)} style={style}>Delete</button>
                    <br/>
                    <span id={`${item.id}-quantity-error`}></span>
                </div>
                <img id={`${item.id}-image`} className="item-image" src={`/api/uploads/${item.fileName}`} alt={item.fileName}/>
            </div>
            )}
        </div>
    )
}