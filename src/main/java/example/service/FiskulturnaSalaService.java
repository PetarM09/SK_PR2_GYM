package example.service;

import example.domen.FiskulturnaSala;

public interface FiskulturnaSalaService {
    FiskulturnaSala sacuvajIliAzurirajSalu(FiskulturnaSala sala);

    FiskulturnaSala vratiSalu(String imeSale);

    String vratiImeSale(String authorization);

    void azurirajImeSaleUser(String ime, String authorization);
}
