package liquibase;

import liquibase.servicelocator.ServiceLocator;
import org.springframework.nativex.domain.init.InitializationDescriptor;
import org.springframework.nativex.hint.*;
import org.springframework.nativex.type.AccessDescriptor;
import org.springframework.nativex.type.HintDeclaration;
import org.springframework.nativex.type.NativeConfiguration;
import org.springframework.nativex.type.TypeSystem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@NativeHint(trigger = Liquibase.class,
        initialization = {
                @InitializationHint(
                        types = liquibase.diff.compare.CompareControl.class,
                        initTime = InitializationTime.RUN
                ),
        },
        types = {
                @TypeHint(types = {
                        liquibase.change.AbstractSQLChange.class,
                        liquibase.database.jvm.JdbcConnection.class,
                }, access = AccessBits.DECLARED_METHODS),
                @TypeHint(types = {
                        liquibase.parser.ChangeLogParserCofiguration.class,
                        liquibase.logging.core.DefaultLoggerConfiguration.class,
                        liquibase.configuration.GlobalConfiguration.class,
                        com.datical.liquibase.ext.config.LiquibaseProConfiguration.class,
                        liquibase.license.LicenseServiceFactory.class,
                        liquibase.executor.ExecutorService.class,
                        liquibase.change.ChangeFactory.class,
                        liquibase.logging.LogFactory.class,
                        liquibase.change.ColumnConfig.class,
                        liquibase.change.AddColumnConfig.class,
                        liquibase.change.core.LoadDataColumnConfig.class,
                        liquibase.sql.visitor.PrependSqlVisitor.class,
                        liquibase.sql.visitor.ReplaceSqlVisitor.class,
                        liquibase.sql.visitor.AppendSqlVisitor.class,
                        liquibase.sql.visitor.RegExpReplaceSqlVisitor.class,
                        liquibase.precondition.Precondition.class,
                }),
                @TypeHint(
                        types = liquibase.change.ConstraintsConfig.class,
                        access = AccessBits.FULL_REFLECTION
                )
        },
        resources = {
                @ResourceHint(isBundle = true, patterns = "liquibase/i18n/liquibase-core"),
                @ResourceHint(patterns = {
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.5.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.6.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.7.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.10.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.0.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.1.xsd",
                        "www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd",
                        "www.liquibase.org/xml/ns/pro/liquibase-pro-3.8.xsd",
                        "www.liquibase.org/xml/ns/pro/liquibase-pro-3.9.xsd",
                        "www.liquibase.org/xml/ns/pro/liquibase-pro-3.10.xsd",
                        "www.liquibase.org/xml/ns/pro/liquibase-pro-4.0.xsd",
                        "www.liquibase.org/xml/ns/pro/liquibase-pro-4.1.xsd",
                        "liquibase.build.properties"
                })
        }
)
public class LiquibaseHints implements NativeConfiguration {
    @Override
    public List<HintDeclaration> computeHints(TypeSystem typeSystem) {
        HintDeclaration hintDeclaration = new HintDeclaration();

        Stream.of(liquibase.change.Change.class,
                liquibase.changelog.ChangeLogHistoryService.class,
                liquibase.command.LiquibaseCommand.class,
                liquibase.database.Database.class,
                liquibase.database.DatabaseConnection.class,
                liquibase.datatype.LiquibaseDataType.class,
                liquibase.diff.compare.DatabaseObjectComparator.class,
                liquibase.diff.DiffGenerator.class,
                liquibase.diff.output.changelog.ChangeGenerator.class,
                liquibase.executor.Executor.class,
                liquibase.license.LicenseService.class,
                liquibase.lockservice.LockService.class,
                liquibase.logging.LogService.class,
                liquibase.parser.ChangeLogParser.class,
                liquibase.parser.NamespaceDetails.class,
                liquibase.parser.SnapshotParser.class,
                liquibase.precondition.Precondition.class,
                liquibase.serializer.ChangeLogSerializer.class,
                liquibase.serializer.SnapshotSerializer.class,
                liquibase.servicelocator.ServiceLocator.class,
                liquibase.snapshot.SnapshotGenerator.class,
                liquibase.sqlgenerator.SqlGenerator.class,
                liquibase.structure.DatabaseObject.class
        ).forEach(t -> addService(hintDeclaration, t));

        hintDeclaration.addInitializationDescriptor(new InitializationDescriptor());

        return Collections.singletonList(hintDeclaration);
    }

    void addService(HintDeclaration hintDeclaration, Class<?> serviceClass) {
        Class<?>[] classes = ServiceLocator.getInstance().findClasses(serviceClass);
        for (Class<?> aClass : classes) {
            hintDeclaration.addDependantType(aClass.getName(), new AccessDescriptor(AccessBits.FULL_REFLECTION));
        }
    }
}
