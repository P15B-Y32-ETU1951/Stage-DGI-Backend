import React from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import "./header.css";

function Header() {
    const role = localStorage.getItem('authRole');
    console.log(role);
    return (
        <div>
            <Navbar bg="primary" variant="dark">
                <Container>
                    

                    <Nav className='ml-auto'>
                        {role && (
                            <Nav.Link as={Link} to={`/${role}`} className='nav-link'>
                                Home
                            </Nav.Link>
                        )}
                        <Nav.Link as={Link} to="/utilisateur-post" className='nav-link'>Sign Up</Nav.Link>
                        <Nav.Link as={Link} to="/Login" className='nav-link'>Log In</Nav.Link>
                        <Nav.Link as={Link} to="/Userinfo" className='nav-link'>User Info</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
        </div>
    );
}

export default Header;
