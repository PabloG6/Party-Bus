package momocorp.partybus.Adapters;

/**
 * Created by Pablo on 12/25/2016.
 */
public interface LoadingCacheListener {

    boolean loadingStart();
    boolean loading();
    boolean loadingFinished();
}

