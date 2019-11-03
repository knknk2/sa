import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './uo-2-o-citizen-dtomf.reducer';
import { IUo2oCitizenDTOMF } from 'app/shared/model/uo-2-o-citizen-dtomf.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUo2oCitizenDTOMFDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Uo2oCitizenDTOMFDetail extends React.Component<IUo2oCitizenDTOMFDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { uo2oCitizenDTOMFEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.uo2oCitizenDTOMF.detail.title">Uo2oCitizenDTOMF</Translate> [
            <b>{uo2oCitizenDTOMFEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.uo2oCitizenDTOMF.name">Name</Translate>
              </span>
            </dt>
            <dd>{uo2oCitizenDTOMFEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.uo2oCitizenDTOMF.uo2oPassportDTOMF">Uo 2 O Passport DTOMF</Translate>
            </dt>
            <dd>{uo2oCitizenDTOMFEntity.uo2oPassportDTOMFId ? uo2oCitizenDTOMFEntity.uo2oPassportDTOMFId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/uo-2-o-citizen-dtomf" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/uo-2-o-citizen-dtomf/${uo2oCitizenDTOMFEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ uo2oCitizenDTOMF }: IRootState) => ({
  uo2oCitizenDTOMFEntity: uo2oCitizenDTOMF.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Uo2oCitizenDTOMFDetail);
