import React from "react"


class UserGroups extends React.Component{

    constructor(props){
        super(props)

        this.state = {
            groups: {}
        }
    }
    componentDidMount(){
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem('token')}`
            }
        };

        fetch(`/api/users/${localStorage.getItem('email')}/groups`,requestOptions)
            .then((res) => res.json())
            .then((res) => {
                this.setState({groups : res})
            }).catch(error => {console.error(error)})
    }
    render (){
        return(
            <ul>
            {
                Array.from(this.state.groups).map(
                    group => <li key={group['id']}><a href={`/groups/${group['id']}`}>{group['name']}</a></li>
                )
            }
        </ul>
        )
    }
}


export default UserGroups