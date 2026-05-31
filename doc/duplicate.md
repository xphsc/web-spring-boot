# 查重策略

## Quick Start

```java
DuplicateCheckCenter<Object> center = new DuplicateCheckCenter<Object>();
DuplicateRule<Object> rule = DuplicateRuleTemplate.<Object>rule("customer-import")
    .threshold(0.88D)
    .fingerprintFields("tenantCode", "contactPhone", "certificateNo")
    .exact("tenantCode", 0.15D)
    .editDistance("customerName", 0.30D)
    .exact("contactPhone", 0.35D)
    .exact("certificateNo", 0.45D)
    .nGram("detailAddress", 0.20D, 3)
    .build();
DuplicateMatchResult<Object> result = center.compare(left, right, rule);
boolean duplicated = result.isMatched();
```

## Spring Boot

`web.duplicate.enabled=true` enables auto configuration.

```yaml
web:
  duplicate:
    enabled: true
```

## Templates

- `DuplicateRuleTemplate.rule("your-scene")`
- `.fingerprintFields(...)`
- `.exact(...) / .editDistance(...) / .jaccard(...) / .nGram(...) / .prefix(...) / .numberEqual(...)`
