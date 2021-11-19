package com.chaev.debts.domain.mappers

import java.lang.RuntimeException

class MappingException(field: String): RuntimeException("Mapping failed on $field")