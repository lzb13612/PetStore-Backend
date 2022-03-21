import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.StaticArray8;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Adoption extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5060008060006101000a81548160ff021916908360ff1602179055506106aa8061003b6000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806335a6861a1461007257806339ac7979146100cf5780633de4eb171461011957806343ae80d31461016c5780634420e486146101d9575b600080fd5b34801561007e57600080fd5b506100b3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610236565b604051808260ff1660ff16815260200191505060405180910390f35b3480156100db57600080fd5b506100fd600480360381019080803560ff169060200190929190505050610353565b604051808260ff1660ff16815260200191505060405180910390f35b34801561012557600080fd5b5061012e61042f565b6040518082600860200280838360005b8381101561015957808201518184015260208101905061013e565b5050505090500191505060405180910390f35b34801561017857600080fd5b50610197600480360381019080803590602001909291905050506104b0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101e557600080fd5b5061021a600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104e5565b604051808260ff1660ff16815260200191505060405180910390f35b600080600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff16141515156102ff576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f75736572206e6f7420726567697374657200000000000000000000000000000081525060200191505060405180910390fd5b600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff169050919050565b6000808260ff161015801561036c575060078260ff1611155b151561037757600080fd5b6000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff161115156103d557600080fd5b3360028360ff166008811015156103e857fe5b0160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550819050919050565b61043761065a565b60026008806020026040519081016040528092919082600880156104a6576020028201915b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001906001019080831161045c575b5050505050905090565b6002816008811015156104bf57fe5b016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600080600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff161415156105ad576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f757365722069732072656769737465726564000000000000000000000000000081525060200191505060405180910390fd5b60008081819054906101000a900460ff168092919060010191906101000a81548160ff021916908360ff160217905550506000809054906101000a900460ff16600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908360ff1602179055506000809054906101000a900460ff169050919050565b610100604051908101604052806008906020820280388339808201915050905050905600a165627a7a723058207b27310da9b539ee620aa75ad9fd37298074a600e0153ba77794718fa8ef4bd80029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":true,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"login\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"petId\",\"type\":\"uint8\"}],\"name\":\"adopt\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getAdopters\",\"outputs\":[{\"name\":\"\",\"type\":\"address[8]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"adopters\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"register\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_LOGIN = "login";

    public static final String FUNC_ADOPT = "adopt";

    public static final String FUNC_GETADOPTERS = "getAdopters";

    public static final String FUNC_ADOPTERS = "adopters";

    public static final String FUNC_REGISTER = "register";

    protected Adoption(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public BigInteger login(String user) throws ContractException {
        final Function function = new Function(FUNC_LOGIN, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt adopt(BigInteger petId) {
        final Function function = new Function(
                FUNC_ADOPT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(petId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void adopt(BigInteger petId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADOPT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(petId)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAdopt(BigInteger petId) {
        final Function function = new Function(
                FUNC_ADOPT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(petId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getAdoptInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADOPT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getAdoptOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ADOPT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public List getAdopters() throws ContractException {
        final Function function = new Function(FUNC_GETADOPTERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray8<Address>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public String adopters(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_ADOPTERS, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt register(String user) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void register(String user, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegister(String user) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRegisterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getRegisterOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public static Adoption load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Adoption(contractAddress, client, credential);
    }

    public static Adoption deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Adoption.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
