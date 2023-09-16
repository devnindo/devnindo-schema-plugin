# `devnindo-schemagen` Plugin

The `devnindo-schemagen` is a companion plugin designed for the Devnindo data type library. It assists in generating
schema representations for classes, making JSON conversion and validation seamless.

## Installation

1. Add the Devnindo data type dependency in your `build.gradle`:

```groovy
dependencies {
    // ... other dependencies
    implementation 'io.devnindo.core:devnindo-datatype:0.9.8'
}
```

2. Incorporate the schemagen plugin:

```groovy
plugins {
    // ... other plugins
    id 'io.devnindo.devnindo-schemagen' version '0.9.17'
}
```

Upon setup, a Gradle task `generateSchema` will be available. Running this task will create the schema representation
for your class.

## Features

1. **Schema-Based Validation**: Automates the creation of schema representations for your Java classes.

2. **JSON Handling**: Facilitates serialization and deserialization between POJOs and their JSON representation.

3. **Diffing**: Computes differences between two instances of the same class.

## Usage

Considering a `JobCircular` class, the plugin will generate a corresponding `$JobCircular` schema class:

- **Static Schema Fields**: Maps Java properties to their JSON counterparts.

- **JSON Deserialization**: Converts a `JsonObject` instance into the corresponding POJO.

- **JSON Serialization**: Transforms a POJO instance into a `JsonObject`.

- **Diffing**: Calculates differences between two POJO instances, producing a delta JSON and a merged instance.

## Example

Given the `JobCircular` class, the plugin generates the `$JobCircular` schema representation that encompasses all the
functionalities as described above.

---
By running this task gradle generate

```java

//import internal.package.DataPageQuery;

import io.devnindo.datatype.schema.DataBean;

import java.time.Instant;


public class JobCircular implements DataBean {


    Integer id;


    Instant publicationDate;


    Instant closingDate;


    String title;


    String docLink;


    String details;


    Instant createdDatime;


    Instant updatedDatime;


    String sourceName;


    String sourceUrl;

    DataPageQuery pageQuery;

    public DataPageQuery getPageQuery() {
        return pageQuery;
    }

    public JobCircular setPageQuery(DataPageQuery pageQuery) {
        this.pageQuery = pageQuery;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public JobCircular setId(Integer id) {
        this.id = id;
        return this;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public JobCircular setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public Instant getClosingDate() {
        return closingDate;
    }

    public JobCircular setClosingDate(Instant closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public JobCircular setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDocLink() {
        return docLink;
    }

    public JobCircular setDocLink(String docLink) {
        this.docLink = docLink;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public JobCircular setDetails(String details) {
        this.details = details;
        return this;
    }

    public Instant getCreatedDatime() {
        return createdDatime;
    }

    public JobCircular setCreatedDatime(Instant createdDatime) {
        this.createdDatime = createdDatime;
        return this;
    }

    public Instant getUpdatedDatime() {
        return updatedDatime;
    }

    public JobCircular setUpdatedDatime(Instant updatedDatime) {
        this.updatedDatime = updatedDatime;
        return this;
    }

    public String getSourceName() {
        return sourceName;
    }

    public JobCircular setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public JobCircular setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }
}

```

to

```java
//import internal.package.DataPageQuery;

import io.devnindo.datatype.json.JsonObject;
import io.devnindo.datatype.schema.BeanSchema;
import io.devnindo.datatype.schema.DataDiff;
import io.devnindo.datatype.schema.SchemaField;
import io.devnindo.datatype.util.Either;
import io.devnindo.datatype.validation.ObjViolation;
import io.devnindo.datatype.validation.Violation;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.time.Instant;

public class $JobCircular extends BeanSchema<JobCircular> {
    public static final SchemaField<JobCircular, String> DOC_LINK = plainField("doc_link", JobCircular::getDocLink, String.class, false);

    public static final SchemaField<JobCircular, DataPageQuery> PAGE_QUERY = beanField("page_query", JobCircular::getPageQuery, DataPageQuery.class, false);

    public static final SchemaField<JobCircular, String> TITLE = plainField("title", JobCircular::getTitle, String.class, false);

    public static final SchemaField<JobCircular, Instant> CREATED_DATIME = plainField("created_datime", JobCircular::getCreatedDatime, Instant.class, false);

    public static final SchemaField<JobCircular, Instant> UPDATED_DATIME = plainField("updated_datime", JobCircular::getUpdatedDatime, Instant.class, false);

    public static final SchemaField<JobCircular, String> SOURCE_URL = plainField("source_url", JobCircular::getSourceUrl, String.class, false);

    public static final SchemaField<JobCircular, String> SOURCE_NAME = plainField("source_name", JobCircular::getSourceName, String.class, false);

    public static final SchemaField<JobCircular, String> DETAILS = plainField("details", JobCircular::getDetails, String.class, false);

    public static final SchemaField<JobCircular, Integer> ID = plainField("id", JobCircular::getId, Integer.class, false);

    public static final SchemaField<JobCircular, Instant> CLOSING_DATE = plainField("closing_date", JobCircular::getClosingDate, Instant.class, false);

    public static final SchemaField<JobCircular, Instant> PUBLICATION_DATE = plainField("publication_date", JobCircular::getPublicationDate, Instant.class, false);

    @Override
    public Either<Violation, JobCircular> apply(JsonObject data) {
        Either<Violation, String> docLinkEither = DOC_LINK.fromJson(data);
        Either<Violation, DataPageQuery> pageQueryEither = PAGE_QUERY.fromJson(data);
        Either<Violation, String> titleEither = TITLE.fromJson(data);
        Either<Violation, Instant> createdDatimeEither = CREATED_DATIME.fromJson(data);
        Either<Violation, Instant> updatedDatimeEither = UPDATED_DATIME.fromJson(data);
        Either<Violation, String> sourceUrlEither = SOURCE_URL.fromJson(data);
        Either<Violation, String> sourceNameEither = SOURCE_NAME.fromJson(data);
        Either<Violation, String> detailsEither = DETAILS.fromJson(data);
        Either<Violation, Integer> idEither = ID.fromJson(data);
        Either<Violation, Instant> closingDateEither = CLOSING_DATE.fromJson(data);
        Either<Violation, Instant> publicationDateEither = PUBLICATION_DATE.fromJson(data);

        ObjViolation violation = newViolation(JobCircular.class);
        violation.check(DOC_LINK, docLinkEither);
        violation.check(PAGE_QUERY, pageQueryEither);
        violation.check(TITLE, titleEither);
        violation.check(CREATED_DATIME, createdDatimeEither);
        violation.check(UPDATED_DATIME, updatedDatimeEither);
        violation.check(SOURCE_URL, sourceUrlEither);
        violation.check(SOURCE_NAME, sourceNameEither);
        violation.check(DETAILS, detailsEither);
        violation.check(ID, idEither);
        violation.check(CLOSING_DATE, closingDateEither);
        violation.check(PUBLICATION_DATE, publicationDateEither);

        if (violation.hasRequirement()) {
            return Either.left(violation);
        }
        JobCircular bean = new JobCircular();
        bean.setDocLink(docLinkEither.right());
        bean.setPageQuery(pageQueryEither.right());
        bean.setTitle(titleEither.right());
        bean.setCreatedDatime(createdDatimeEither.right());
        bean.setUpdatedDatime(updatedDatimeEither.right());
        bean.setSourceUrl(sourceUrlEither.right());
        bean.setSourceName(sourceNameEither.right());
        bean.setDetails(detailsEither.right());
        bean.setId(idEither.right());
        bean.setClosingDate(closingDateEither.right());
        bean.setPublicationDate(publicationDateEither.right());
        return Either.right(bean);
    }

    @Override
    public JsonObject apply(JobCircular bean) {
        JsonObject js = new JsonObject();
        js.put(DOC_LINK.name, DOC_LINK.toJson(bean));
        js.put(PAGE_QUERY.name, PAGE_QUERY.toJson(bean));
        js.put(TITLE.name, TITLE.toJson(bean));
        js.put(CREATED_DATIME.name, CREATED_DATIME.toJson(bean));
        js.put(UPDATED_DATIME.name, UPDATED_DATIME.toJson(bean));
        js.put(SOURCE_URL.name, SOURCE_URL.toJson(bean));
        js.put(SOURCE_NAME.name, SOURCE_NAME.toJson(bean));
        js.put(DETAILS.name, DETAILS.toJson(bean));
        js.put(ID.name, ID.toJson(bean));
        js.put(CLOSING_DATE.name, CLOSING_DATE.toJson(bean));
        js.put(PUBLICATION_DATE.name, PUBLICATION_DATE.toJson(bean));
        return js;
    }

    @Override
    public DataDiff<JobCircular> diff(JobCircular left, JobCircular right) {
        JobCircular merged = new JobCircular();
        JsonObject delta = new JsonObject();

        merged.docLink = DOC_LINK.diff(left, right, delta::put);
        merged.pageQuery = PAGE_QUERY.diff(left, right, delta::put);
        merged.title = TITLE.diff(left, right, delta::put);
        merged.createdDatime = CREATED_DATIME.diff(left, right, delta::put);
        merged.updatedDatime = UPDATED_DATIME.diff(left, right, delta::put);
        merged.sourceUrl = SOURCE_URL.diff(left, right, delta::put);
        merged.sourceName = SOURCE_NAME.diff(left, right, delta::put);
        merged.details = DETAILS.diff(left, right, delta::put);
        merged.id = ID.diff(left, right, delta::put);
        merged.closingDate = CLOSING_DATE.diff(left, right, delta::put);
        merged.publicationDate = PUBLICATION_DATE.diff(left, right, delta::put);

        return new DataDiff<>(delta, merged);
    }
}

```
