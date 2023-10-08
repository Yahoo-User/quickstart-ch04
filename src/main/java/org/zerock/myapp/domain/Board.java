package org.zerock.myapp.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data

// ------------
// 3rd. style.	- @GeneratedValue(generator = "SequenceGenerator", strategy = GenerationType.SEQUENCE)
// ------------
/*
@SequenceGenerator(
	name = "BOARD_SEQ_GENERATOR",
	sequenceName = "BOARD_SEQUENCE",
	initialValue = 1,
	allocationSize = 1
)
*/

// ------------
// 5th. style 	- @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
// ------------
/*
@TableGenerator(
	// (Required) A `unique` generator name that can be referenced by `one or more` classes
	// to be the generator for `id` values.
	name = "BOARD_TBL_GENERATOR",

	// (Optional) `Name of table` that stores the `generated id` values.
	//			  Defaults to a name `chosen by persistence provider`.
	table = "t_board_id",

	// (Optional) Name of the `primary key column` in the `table`.
	//			  Defaults to a `provider-chosen` name.
	pkColumnName = "",

	// (Optional) The `initial value` to be used to initialize the column that stores the `last value generated`.
	initialValue = 1,

	// (Optional) The `amount to increment by` when allocating `id` numbers from the generator.
	allocationSize = 1
)
*/

/*
 * ----------------------------------
 * @Entity
 * ----------------------------------
 * Specifies that the class is an `entity`.
 * This annotation is applied to the `entity` class.
 */
@Entity

/*
 * ----------------------------------
 * @Table
 * ----------------------------------
 * Specifies the `primary table` for the `annotated entity`. (***)
 * Additional tables may be specified using `@SecondaryTable` or `@SecondaryTables` annotation.
 *
 * If no `@Table` annotation is specified for an `entity` class, the default values apply. (***)
 * 	- `name` property	: (Optional) The `name` of the table. 		Defaults to the `entity name`.
 * 	- `catalog` property: (Optional) The `catalog` of the table. 	Defaults to the `default` catalog.
 * 	- `schema` property : (Optional) The `schema` of the table. 	Defaults to the `default schema` for user.
 *
 * 	Example:
 *      @Entity
 *      @Table(name="CUST", schema="RECORDS")
 *      public class Customer { ... }
 */
@Table(name="board")
//@Table(
//	name = "board",
//	uniqueConstraints = {
//		@UniqueConstraint(columnNames = { "title", "writer" })
//	}
//)
public class Board implements Serializable {
	// 'serialVersionUID' can be annotated with '@Serial' annotation
	@Serial private static final long serialVersionUID = 1L;

	/*
	 * ----------------------------------
	 * @Id
	 * ----------------------------------
	 * specifies the `primary key` of an `entity`. (***)
	 *
	 * The field or property to which the `@Id` annotation is applied should be one of the following types: (***)
	 *
	 * 		(1) any Java primitive type;
	 * 		(2)	any primitive wrapper type;
	 * 		(3)	String;
	 * 		(4)	java.util.Date;
	 * 		(5)	java.sql.Date;
	 * 		(6)	java.math.BigDecimal;
	 * 		(7)	java.math.BigInteger.
	 *
	 * The `mapped column` for the primary key of the entity is assumed to be the `primary key` of the `primary` table.
	 *
	 * If no `@Column` annotation is specified,
	 * The `primary key` column name is assumed to be the name of the `primary key` property or field.
	 *
	 * 	Example:
	 *     @Id
	 *     public Long getId() { return id; }
	 */
	@Id

	// ------------
	// 1st. style.		- OK : for H2 Database.
//	@GeneratedValue
//	@GeneratedValue(strategy = GenerationType.AUTO)

	// ------------
	// 2nd. style.		- OK : for H2 Database.
//	@GeneratedValue(generator = "incrementer")
//	@GenericGenerator(name = "incrementer", strategy = "increment")

	// ------------
	// 3rd. style.		- OK : for H2 Database.
//	@GeneratedValue(generator = "SequenceGenerator", strategy = GenerationType.SEQUENCE)
//	@SequenceGenerator(name = "SequenceGenerator", initialValue = 6, allocationSize = 1)

//	@GeneratedValue(generator = "BOARD_SEQ_GENERATOR", strategy = GenerationType.SEQUENCE)

	// ------------
	// 4th. style.		- OK : for H2 Database.
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	// ------------
	// 5th. style		- ?? : for H2 Database.
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_TBL_GENERATOR")
	private Long seq;

	/*
	 * ----------------------------------
	 * @Basic
	 * ----------------------------------
	 * This annotation is used to map a `basic attribute type` to a `database column`. (***)
	 * The simplest type of mapping to a database column.
	 *
	 * The `@Basic` annotation can be applied to a `persistent property` or `instance variable` of any of the following types :
	 *
	 * 		01) Java primitive types,
	 * 		02) wrappers of the primitive types,
	 * 		03) String,
	 * 		04) java.math.BigInteger, java.math.BigDecimal,
	 * 		05) java.util.Date, java.util.Calendar, java.sql.Date,
	 * 		06) java.sql.Time, java.sql.Timestamp,
	 * 		07) byte[], Byte[],
	 * 		08) char[], Character[],
	 * 		09) enums,
	 * 		10) any other type that implements `java.io.Serializable`.
	 *
	 * The use of the `@Basic` annotation is `optional` for persistent fields and properties of these types.
	 *
	 * If the `@Basic` annotation is *NOT* specified for such a field or property,
	 * the default values of the `@Basic` annotation will apply.
	 *
	 * 	Example 1:
	 *
	 *      @Basic
	 *      protected String name;
	 *
	 *  Example 2:
	 *
	 *      @Basic(fetch=LAZY)
	 *      protected String getName() { return name; }
	 */
//	@Basic
//	@Basic(fetch = FetchType.EAGER, optional = true)	// Default Values.
	@Basic(optional = false)
	private String title;

/*
	@Column(	// Default Values.
		// (Optional) The name of the column.
		// 			  Defaults to the `property` or `field` name.
		name = "",

		// (Optional) Whether the column is a unique key.
		// 			  This is a shortcut for the `@UniqueConstraint` annotation at the table level and is useful
		// 			  for when the `unique key constraint` corresponds to *Only* a `single column`.
		// 			  This constraint applies in addition to any constraint entailed by `primary key` mapping
		// 			  and to constraints specified at the table level.
		unique = false,

		// (Optional) Whether the `database column` is `nullable`.
		nullable = true,

		// (Optional) Whether the `column` is included in `SQL INSERT` statements generated by the persistence provider.
		insertable = true,

		// (Optional) Whether the `column` is included in `SQL UPDATE` statements generated by the persistence provider.
		updatable = true,

		// (Optional) The `SQL fragment` that is used when generating the `DDL for the column`.
		//			  Defaults to the generated SQL to create a column of the `inferred` type.
		columnDefinition = "",

		// (Optional) The name of the table that contains the `column`.
		// 			  If absent, the `column` is assumed to be in the `primary table`.
		table = "",

		// (Optional) The column length.
		// 			  (Applies only if a `string-valued` column is used.)
		length = 255,

		// (Optional) The precision for a `decimal` (exact `numeric`) column.
		// 			  (Applies *Only* if a `decimal` column is used.)
		// 			  Value must be set by developer if used when generating the `DDL` for the `column`.
		precision = 0,

		// (Optional) The scale for a `decimal` (exact `numeric`) column.
		// 			  (Applies only if a `decimal` column is used.)
		scale = 0
	)
*/

	/*
	 * ----------------------------------
	 * @Column
	 * ----------------------------------
	 * Specifies the `mapped column` for a `persistent` property or field.
	 * If no `@Column` annotation is specified, the default values apply.
	 *
	 * 	Example 1:
	 *      @Column( name="DESC", nullable=false, length=512 )
	 *      public String getDescription() { return description; }
	 *
	 *	Example 2:
	 *      @Column( name="DESC",
	 *              columnDefinition="CLOB NOT NULL",
	 *              table="EMP_DETAIL" )
	 *      @Lob
	 *      public String getDescription() { return description; }
	 *
	 *	Example 3:
	 *      @Column( name="ORDER_COST", updatable=false, precision=12, scale=2 )
	 *      public BigDecimal getCost() { return cost; }
	 */
	@Column
	private String writer;

	private String content;

	@Column(columnDefinition = "bigint default 0", insertable = false)
	private Long cnt;

	/*
	 * ----------------------------------
	 * @Transient
	 * ----------------------------------
	 * Specifies that the `property` or `field` is *NOT* `persistent`.
	 * It is used to annotate a property or field of an
	 *
	 * 	(1) `entity` class
	 * 	(2) `mapped super` class, or
	 * 	(3) `embeddable` class.
	 *
	 * 	Example:
	 *
	 *      @Entity
	 *      public class Employee {
	 *          @Id int id;
	 *          @Transient User currentUser;
	 *          ...
	 *      }
	 */
	@Transient
	private String transientField;	// Excluded from the mapping.

	/*
	 * ----------------------------------
	 * @Temporal(TemporalType.[DATE|TIME|TIMESTAMP]) - [ METHOD, FIELD ]
	 * ----------------------------------
	 * This annotation must be specified for persistent fields or properties of type `java.util.Date` and `java.util.Calendar`.
	 * It may *ONLY* be specified for fields or properties of these types.
	 *
	 * This annotation may be used in conjunction with the `@Basic` annotation, the `@Id` annotation,
	 * or the `@ElementCollection` annotation (when the element collection value is of such a temporal type).
	 *
	 * 	Example:
	 *
	 *  	@Temporal(DATE)
	 *  	protected `java.util.Date` endDate;
	 */
	@Temporal(TemporalType.TIMESTAMP)

	// In the `H2`, `Oracle` Cloud Version Database,
	// 		SELECT current_date, current_time, current_timestamp;
//	@Column(columnDefinition = "date default current_date", insertable = false)	// in H2, Oracle
	@Column(columnDefinition = "timestamp default current_timestamp", insertable = false)	// in H2, Oracle
	private Date write_date;		// 작성일시

	/*
	 * ----------------------------------
	 * @CreationTimestamp - [ FIELD, METHOD ]
	 * ----------------------------------
	 * Marks a property as the `creation timestamp` of the containing entity. (***)
	 * The property value will be set to the current `VM date` exactly *ONCE*
	 * when `saving` the owning entity for the first time. (***)
	 *
	 * Supported property types:
	 * 	 1) Date  2) Calendar  3) Date  4) Time  5) Timestamp  6) Instant  7) LocalDate  8) LocalDateTime
	 *   9) LocalTime  10) MonthDay  11) OffsetDateTime  12) OffsetTime  13) Year  14) YearMonth  15) ZonedDateTime
	 *
	 * (Caution) Must be *NOT* `insertable = false` on the `@Column`.
	 */
	@CreationTimestamp	// Automatically Timestamp Generated. (***)
//	@Column(insertable = true)		// OK
	private LocalDateTime insert_ts;

	/*
	 * ----------------------------------
	 * @UpdateTimestamp - [ FIELD, METHOD ]
	 * ----------------------------------
	 * Marks a property as the `update timestamp` of the containing entity. (***)
	 * The property value will be set to the current `VM date` whenever the owning entity is `updated`. (***)
	 *
	 * Supported property types:
	 * 	 1) Date  2) Calendar  3) Date  4) Time  5) Timestamp  6) Instant  7) LocalDate  8) LocalDateTime
	 *   9) LocalTime  10) MonthDay  11) OffsetDateTime  12) OffsetTime  13) Year  14) YearMonth  15) ZonedDateTime
	 *
	 * (Caution) Must be *NOT* `insertable = false` on the `@Column`.
	 */
	@UpdateTimestamp	// Automatically Timestamp Generated. (***)
//	@Column(insertable = true)		// OK
	private LocalDateTime update_ts;
	
	
   
} // end class
