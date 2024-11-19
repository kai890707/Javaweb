package com.example.javaweb.service.walletService;
import com.example.javaweb.entity.Wallet;
import java.util.List;

public interface WalletServiceInterface {

    List<Wallet> getAllWallet();

    List<Wallet> getWalletsWithDeleted();

    List<Wallet> getDeletedWallets();

    Wallet getWalletById(Long id);

    Wallet createWallet(Wallet wallet);

    Wallet updateWallet(Long userId, Wallet wallet);

    Boolean deleteWallet(Long userId);
}
