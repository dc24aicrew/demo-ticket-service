from diagrams import Diagram, Cluster
from diagrams.onprem.compute import Server
from diagrams.programming.framework import Spring

def generate_component_interactions_diagram():
    with Diagram("Component Interactions", show=False, filename="wiki/images/component_interactions", outformat="svg"):
        rest_controller = Server("REST Controller\n(API Layer)")
        service_port = Spring("Service Port\n(Domain Layer)")
        service_impl = Spring("Service Impl\n(Use Case Layer)")
        repository_port = Spring("Repository Port\n(Domain Layer)")
        repository_adapter = Server("Repository Adapter\n(Infra Layer)")
        jpa_repository = Server("JPA Repository\n(Infra Layer)")
        
        rest_controller >> service_port >> service_impl >> repository_port >> repository_adapter >> jpa_repository

if __name__ == "__main__":
    generate_component_interactions_diagram()
    print("Diagram generated successfully!")