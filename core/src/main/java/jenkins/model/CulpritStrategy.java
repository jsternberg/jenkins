package jenkins.model;

import hudson.ExtensionPoint;
import hudson.model.AbstractBuild;
import hudson.model.AbstractDescribableImpl;
import hudson.model.User;
import hudson.util.AdaptedIterator;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public abstract class CulpritStrategy extends AbstractDescribableImpl<CulpritStrategy> implements ExtensionPoint {

    /**
     * Retrieves a list of users who may have broken the build.
     *
     * Extensions can either implement this method directly or implement
     * getCulpritNames and let this method convert them into users.
     */
    public Set<User> getCulprits(AbstractBuild<?,?> build) {
        final Set<String> culprits = getCulpritNames(build);
        return new AbstractSet<User>() {
            public Iterator<User> iterator() {
                return new AdaptedIterator<String,User>(culprits.iterator()) {
                    protected User adapt(String id) {
                        return User.get(id);
                    }
                };
            }

            public int size() {
                return culprits.size();
            }
        };
    }

    /**
     * Retrieves culprits and returns them by name (instead of by User).
     */
    public Set<String> getCulpritNames(AbstractBuild<?,?> build) {
        throw new UnsupportedOperationException("Either implement getCulpritNames(AbstractBuild<?,?>) or override getCulprits(AbstractBuild<?,?>)");
    }

    @Override
    public CulpritStrategyDescriptor getDescriptor() {
        return (CulpritStrategyDescriptor)super.getDescriptor();
    }
}
