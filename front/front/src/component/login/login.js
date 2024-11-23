import React, { useState } from 'react';
import { Button, Form, Container, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [utilisateur, setUtilisateur] = useState({
       
        email: "",
        password: "",
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setUtilisateur({
            ...utilisateur,
            [name]: value
        });
    };

    const navigate = useNavigate();
    const handleSubmit = async(event) => {
        event.preventDefault();
        console.log(utilisateur);

try {
    const response = await fetch('http://localhost:8080/api/v1/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(utilisateur)
    });
    const data = await response.json();
    const token = data.token; 
    const role=data.role; // Assume que le serveur renvoie un objet JSON avec un champ 'token'
            
            // Sauvegarde le token dans localStorage
            localStorage.setItem('authToken', token);
            localStorage.setItem('authRole', role);
            
            console.log('Token saved:', token);
            console.log('Role:', role);
            navigate(`/${role}`);

} catch (error) {
    console.error("erreur de connexion ,veuillez ressayer correctement:",error.message);
}
    };
    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md={6}>
                    <h1 className="text-center my-4">Log In</h1>
                    <h5>Veuillez vous connecter </h5>
                    <Form onSubmit={handleSubmit}>
                       

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control 
                                type="email" 
                                placeholder="Entrer email" 
                                name="email"
                                value={utilisateur.email} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control 
                                type="password" 
                                placeholder="Entrer mot de passe" 
                                name="password"
                                value={utilisateur.password} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Button variant="primary" type="submit" className="w-100">
                            Submit
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default Login;
